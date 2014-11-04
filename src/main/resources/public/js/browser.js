(function() {
	'use strict';
	var files = {};
	var path = "";
	app = angular.module('browser', []);
	app.controller('BrowserController', [ '$http', '$location', '$routeParams', function($http, $location, $routeParams) {
		var browser = this;
		
		this.loadPath = function(relPath) {
			path = relPath;
			$http.get('/api/tree?relPath=' + encodeURIComponent(path)).success(function(data) {
				browser.files = data;
			});
		};
		
		this.click = function(file) {
			if(file.directory) {
				$location.path('/browser/' + encodeURIComponent(file.relativePath));
				//this.loadPath(file.relativePath);
			} else {
				$location.path('/editor/' + encodeURIComponent(file.relativePath));
			}
		};
		
		this.loadPath($routeParams.folder?$routeParams.folder:'');//
		
	} ]);
}());
