const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    mainPage: path.resolve(__dirname, 'src', 'pages', 'mainPage.js'),
    collectionPage: path.resolve(__dirname, 'src', 'pages', 'collectionPage.js'),
    gamePage: path.resolve(__dirname, 'src', 'pages', 'gamePage.js')
//    cardPage: path.resolve(__dirname, 'src', 'pages', 'cardPage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/collectionPage.html',
      filename: 'collectionPage.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/gamePage.html',
      filename: 'gamePage.html',
      inject: false
    }),
//    new HtmlWebpackPlugin({
//      template: './src/cardPage.html',
//      filename: 'cardPage.html',
//      inject: false
//    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/images'),
          to: path.resolve("dist/images")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
