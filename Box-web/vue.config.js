const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: 'localhost',
        port: 8080,
    },
    configureWebpack: {
        devtool: process.env.NODE_ENV === 'production' ? 'source-map' : 'eval-source-map',
    }
})


