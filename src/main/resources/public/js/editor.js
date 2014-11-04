(function() {
	'use strict';

	app = angular.module('editor', ['ui.ace']);
	app.controller('EditorController', [ '$http', '$location', '$routeParams', function($http, $location, $routeParams) {
		var editor = this;
		this.content = "File content here!";
		
		$http.get('/api/resource?path=' + $routeParams.file).success(function(data) {
			editor.content = data;
		});
		
	} ]);
}());
