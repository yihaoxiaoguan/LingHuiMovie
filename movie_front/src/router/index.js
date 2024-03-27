import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'


Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'User_Home',
    component: () => import('../views/User/UserHome.vue'),
    children: [
      {
        path:'Personal',
        name:'Personal',
        component:()=>import('../views/User/Personal.vue'),
        children:[
              {
                path:'PerInfo',
                name:'PerInfo',
                component:()=>import('../views/User/Personal_Info.vue')
              },
              {
                path:'PerOrder',
                name:'PerOrder',
                component:()=>import('../views/User/Personal_Orders.vue')
              },
              {
                path:'PerUpdate',
                name:'PerUpdate',
                component:()=>import('../views/User/Personal_Update.vue')
              },
              {
                path:'PerPassword',
                name:'PerPassword',
                component:()=>import('../views/User/Personal_Password.vue')
              }
          ]
          
          },
          {
            path:'home',
            name:'home',
            component:()=>import('../views/User/Movie_Home.vue')
          },
          {
            path:'MovieInfo/:id',
            name:'MovieInfo',
            component:()=>import('../views/User/Movie_Info.vue')
          },
          {
            path:'cinemas_movieId=:id',
            name:'cinemas',
            component:()=>import('../views/User/Cinemas.vue')
          },
          {
            path:'Movies',
            name:'Movies',
            component:()=>import('../views/User/Movies.vue')
          },
          {
            path:'cinema',
            name:'cinema',
            component:()=>import('../views/User/cinema.vue')
          },{
            path:'cinemaId=:id',
            name:'cinemaId',
            component:()=>import('../views/User/Cinema_Info.vue')
          },
          {
            path:'showtime=:showtimeId&movieId=:movieId&cinemaId=:cinemaId',
            name:'ChooseSeat',
            component:()=>import('../views/User/ChooseSeat.vue')
          },
    ]
  },
    {
    path: '/Admin',
    name: 'Admin_Home',
    meta: { name: '主页' },
    component: () => import('../views/Admin/AdminHome.vue'),
    children: [
      {
        path: 'UserManage',
        name: 'UserManage',
        meta: { name: '用户列表' },
        component: () => import('../views/Admin/UserManage')
      },
      {
        path:'UserManager',
        name:'UserManager',
        meta: { name: '用户列表' },
        component:()=>import('../views/Admin/UserManager')
      },
      {
        path: 'OrderManager',
        name: 'OrderManager',
        meta: { name: '订单列表' },
        component: () => import('../views/Admin/OrderManager')
      },
      {
        path: 'MovieOnIn',
        name: 'MovieOnIn',
        meta: { name: '在映电影' },
        component: () => import('../views/Admin/MovieOnIn')
      },
      {
        path: 'MovieAdd',
        name: 'MovieAdd',
        meta: { name: '上映电影' },
        component: () => import('../views/Admin/MovieAdd')
      }
    ]
  },
  {
    path: '/login',
    name: 'login_user',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register_user',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/ChooseSeat',
    name: 'ChooseSeat',
    component: () => import('../views/User/ChooseSeat.vue')
  },
  {
    path: '/403',
    name: '403',
    component: () => import('../views/403.vue')
  },{
    path: '/*',
    name: '404',
    component: () => import('../views/404.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let adminPaths = ['/Admin,/Admin/*']; // 定义管理员可以访问的路由路径
  let user = JSON.parse(localStorage.getItem('Moive_user') || '{}'); // 从本地存储获取用户数据

  console.log(to);
  
  if (user.role !== "管理员" && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，并且当前要访问的路径是管理员才能访问的路径，那么重定向到/403页面
    next('/403');
  } else {
    next(); // 允许用户继续访问该路由
  }
});

export default router
