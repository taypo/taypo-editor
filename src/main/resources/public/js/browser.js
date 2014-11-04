(function() {
	'use strict';
	var files = {};
	var path = "";
	app = angular.module('browser', []);
	app.controller('BrowserController', [ '$http', '$location', function($http, $location) {
		var browser = this;

		this.loadPath = function(relPath) {
			path = relPath;
			$http.get('/api/tree?relPath=' + encodeURIComponent(path)).success(function(data) {
				browser.files = data;
			});
		};
		
		this.click = function(file) {
			if(file.directory) {
				this.loadPath(file.relativePath);
			} else {
				// TODO open file
				$location.path('/editor/' + encodeURIComponent(file.relativePath));
			}
		};
		
		this.loadPath('');
	} ]);
}());
