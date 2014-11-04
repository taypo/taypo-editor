(function() {
	'use strict';

	window.app = angular.module('taypo-editor', [ 'ngRoute', 'browser', 'editor' ]);

	app.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/browser', {
			templateUrl : 'partials/browser.html',
			controller : 'BrowserController as browser'
		}).when('/editor/:file', {
			templateUrl : 'partials/editor.html',
			controller : 'EditorController as editor'
		}).otherwise({
			redirectTo : '/browser'
		});
	} ]);

}());
