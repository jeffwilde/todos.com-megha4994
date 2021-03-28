/*global Backbone */
var app = app || {};

(function () {
	'use strict';

	// Todo Router
	// ----------
	app.TodoRouter = Backbone.Router.extend({
		routes: {
			'*filter(/:color)': 'setFilter'
		},

		setFilter: function (param, color) {
			// Set the current filter to be used
			app.TodoFilter = param || '';
			app.TodoTag = color || '';

			// Trigger a collection filter event, causing hiding/unhiding
			// of Todo view items
			app.todos.trigger('filter');
		}
	});
})();
