(function() {
	'use strict';

	app = angular.module('viewer', []);
	app.controller('ViewerController', [
			'$http',
			'$location',
			'$routeParams',
			'$scope',
			'marked',
			function($http, $location, $routeParams, $scope, marked) {
				var viewer = this;
				this.content = "File content here!";
				this.file = $routeParams.file;
				this.frontMatter = '';
				this.isMarkdown = this.file.split('.').pop() == 'md';

				$http.get('/api/resource?path=' + viewer.file).success(
						function(data) {
							viewer.content = data;
							if(viewer.isMarkdown) {
								viewer.content = viewer.content.replace(/---[\s\S]*?---/, '');
							}
						});

				this.edit = function() {
					$location.path('/editor/' + viewer.file);
				};


			} ]);
}());
