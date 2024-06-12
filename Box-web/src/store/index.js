import Vue from 'vue'
import Vuex from 'vuex'

import router, {resetRouter} from "@/router";
// 引入vuex-persistedstate插件用于状态持久化
import createPersistedState from "vuex-persistedstate";

// 使用Vuex插件
Vue.use(Vuex)

// 动态添加新路由的函数
function addNewRoute(menuList) {
    // 获取当前路由配置
    let routes = router.options.routes;
    // 遍历路由配置
    routes.forEach(routeItem => {
        // 找到Index路由
        if (routeItem.path === "/Index") {
            // 遍历菜单列表，为每个菜单项添加子路由
            menuList.forEach(menu => {
                let childRoute = {
                    path: '/' + menu.menuclick,
                    name: menu.menuname,
                    meta: {
                        title: menu.menuname
                    },
                    // 使用懒加载的方式加载组件
                    component: () => import('../components/' + menu.menucomponent)
                };
                // 将子路由添加到Index路由的children中
                routeItem.children.push(childRoute);
            });
        }
    });
    // 重置路由
    resetRouter();
    // 将新的路由配置添加到router中
    router.addRoutes(routes);
}

// 创建Vuex Store实例
export default new Vuex.Store({
    // 定义状态
    state: {
        menu: [],
        user: JSON.parse(localStorage.getItem('user')) || null  // 从localStorage中获取用户状态或设置为null
    },
    // 定义修改状态的方法
    mutations: {
        // 设置菜单状态
        setMenu(state, menuList) {
            state.menu = menuList;
            addNewRoute(menuList); // 添加新路由
            localStorage.setItem('menu', JSON.stringify(menuList)); // 将菜单状态保存到localStorage
        },
        // 设置用户状态
        setUser(state, user) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user)); // 将用户状态保存到localStorage
        },
    },
    // 定义获取状态的方法
    getters: {
        // 获取菜单状态
        getMenu(state) {
            return state.menu;
        },
        // 获取用户状态
        getUser(state) {
            return state.user;
        },
    },
    // 使用插件
    plugins: [
        // 使用vuex-persistedstate插件进行状态持久化
        createPersistedState({
            paths: ['menu', 'user'], // 指定需要持久化的状态
        }),
    ],
});
