(function() {
	'use strict';
	var files = {};
	var path = "";
	app = angular.module('browser', []);
	app.controller('BrowserController', [ '$http', function($http) {
		var browser = this;
		
		/*
		$http.get('/api/tree').success(function(data) {
			browser.files = data;
		});
		*/
		this.loadPath = function(relPath) {
			path = relPath;
			$http.get('/api/tree?relPath=' + path).success(function(data) {
				browser.files = data;
			});
		};
		
		this.click = function(file) {
			if(file.directory) {
				this.loadPath(file.relativePath);
			} else {
				// TODO open file
			}
		};
		
		this.loadPath('');
	} ]);
}());
