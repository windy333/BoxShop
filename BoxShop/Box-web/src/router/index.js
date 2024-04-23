import VueRouter from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/Login')
    },
    {
        path: '/Index',
        name: 'index',
        component: () => import('../components/Index'),
        children: [
            {
                path: '',
                title: "首页",
                name: 'home',
                component: () => import ('../components/Home')
            },
            {
                path: '/PersonCenter',
                name: 'PersonCenter',
                meta: {
                    title: "个人中心"
                },
                component: () => import('../components/PersonCenter')
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export function resetRouter() {
    const newRouter = new VueRouter({
        mode: 'history',
        routes: []
    })
    router.matcher = newRouter.matcher
}

export default router;