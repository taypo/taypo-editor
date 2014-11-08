(function() {
	'use strict';

	app = angular.module('editor', [ 'ui.ace' ]);
	app.controller('EditorController', [
			'$http',
			'$location',
			'$routeParams',
			'$scope',
			function($http, $location, $routeParams, $scope) {
				var editor = this;
				this.content = "File content here!";

				$http.get('/api/resource?path=' + $routeParams.file).success(
						function(data) {
							editor.content = data;
						});
				
				$scope.aceLoaded = function(_editor){
				    // Editor part
				    var _session = _editor.getSession();
				    var _renderer = _editor.renderer;

				    // Options
				    //_editor.setReadOnly(true);
				    
				    _session.setMode(getMode($routeParams.file));
				    _session.setUndoManager(new ace.UndoManager());
				    //_renderer.setShowGutter(false);

				    // Events
				    // _editor.on("changeSession", function(){ ... });
				    // _session.on("change", function(){ ... });
				    _editor.setTheme('ace/theme/github');
				  };

			} ]);
}());

function getMode(filename) {
	switch(ext = filename.split('.').pop()) {
	case 'md':
		return 'ace/mode/markdown';
	case 'js':
		return 'ace/mode/javascript';
	case 'yml':
		return 'ace/mode/yaml';
	default:
		return 'ace/mode/' + ext;
	}
}
