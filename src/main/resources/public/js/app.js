(function() {
	'use strict';

	window.app = angular.module('taypo-editor', [ 'ngRoute', 'browser', 'viewer', 'editor' ]);

	app.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/browser/:folder?', {
			templateUrl : 'partials/browser.html',
			controller : 'BrowserController as browser'
		}).when('/viewer/:file', {
			templateUrl : 'partials/viewer.html',
			controller : 'ViewerController as viewer'
		}).when('/editor/:file', {
			templateUrl : 'partials/editor.html',
			controller : 'EditorController as editor'
		}).otherwise({
			redirectTo : '/browser/'
		});
	} ]);

	app.controller('RepoController', [ '$http', '$location', '$routeParams', '$scope',
			function($http, $location, $routeParams, $scope) {
				var repo = this;
				$http.get('/api/repo/info').success(function(data) {
					repo.info = data;
				});
			} ]);

}());
