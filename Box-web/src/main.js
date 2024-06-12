import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import VueRouter from 'vue-router';
import router from './router';
import store from './store';

Vue.prototype.$axios = axios;
//Vue.prototype.$httpUrl = 'http://localhost:8088'
Vue.prototype.$httpUrl =process.env.VUE_APP_BASE_API;
Vue.config.productionTip = false

Vue.use(VueRouter);
Vue.use(ElementUI);
new Vue({
    router,
    store,
    render: h => h(App),
    created() {
        // 检查localStorage中是否有菜单列表
        const menuList = JSON.parse(localStorage.getItem('menu'));
        if (menuList) {
            // 如果有，使用它们重新添加路由
            this.$store.commit('setMenu', menuList);
        }
    }
}).$mount('#app')
