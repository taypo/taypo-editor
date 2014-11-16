(function() {
	'use strict';
	var files = {};
	var parents = [];
	var path = "";
	app = angular.module('browser', []);
	app.controller('BrowserController', [ '$http', '$location', '$routeParams',
			function($http, $location, $routeParams) {
				var browser = this;

				this.loadPath = function(relPath) {
					browser.path = relPath;
					$http.get('/api/tree?relPath=' + encodeURIComponent(browser.path)).success(function(data) {
						browser.files = data;
					});
					browser.parents = [ {
						name : 'Home',
						relativePath : '',
						directory : true
					} ];
					getAllPaths('', '/' + decodeURIComponent(browser.path)).forEach(function(entry) {
						browser.parents.push({
							name : entry.split('/').pop(),
							relativePath : entry,
							directory : true
						});
					});

				};

				this.click = function(file) {
					if (file.directory) {
						$location.path('/browser/' + encodeURIComponent(file.relativePath));
						// this.loadPath(file.relativePath);
					} else {
						$location.path('/viewer/' + encodeURIComponent(file.relativePath));
					}
				};

				this.loadPath($routeParams.folder ? $routeParams.folder : '');//

			} ]);
}());

function getAllPaths(topLevelDirectory, currentDirectory) {

	// split into individual directories
	var topLevelSegments = topLevelDirectory.split("/");
	var currentSegments = currentDirectory.split("/");

	var permutation = topLevelDirectory;
	var allPaths = [ ];

	// start appending directories that sit below topLevelDirectory
	for (i = topLevelSegments.length; i < currentSegments.length; i++) {
		permutation = permutation + "/" + currentSegments[i];
		allPaths.push(permutation.replace(/^\/|\/$/g, ''));
	}
	return allPaths;
}
