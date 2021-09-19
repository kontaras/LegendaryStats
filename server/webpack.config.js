const path = require('path');

module.exports = {
	entry: './src/main/js/utils.js',
	cache: true,
	mode: "development",
	devtool: 'cheap-module-source-map',
	output: {
		path: __dirname,
		filename: './src/main/resources/static/scripts/bundle.js'
	},
	
};