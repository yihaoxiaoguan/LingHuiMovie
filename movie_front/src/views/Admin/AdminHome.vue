<!-- 这个是后台管理页面侧边栏和头部的组件 -->

<template>
  <div>
    <el-container>
      <el-aside class="el_aside">
        <div class="aside_logo">
          <img src="../../assets/image/logo.png" alt="logo" class="logo_image">
        </div>
        <el-menu open router :default-active="$route.path" style="border: none;background-color:#ecefff;">
          <el-menu-item index="/Admin"><i class="el-icon-zhuye"></i>系统首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <span><i class="el-icon-dianying"></i>影片管理</span>
            </template>
            <el-menu-item index="/Admin/MovieOnIn">在映电影</el-menu-item>
            <el-menu-item index="/Admin/MovieAdd">上映电影</el-menu-item>
            <el-menu-item index="2-3">影院排片</el-menu-item>
            <el-menu-item index="2-4">海报管理</el-menu-item>
          </el-submenu>
          <el-menu-item index="/Admin/OrderManager"><i class="el-icon-dianyingpiao"></i>订单管理</el-menu-item>
          <el-submenu index="4">
            <template slot="title">
              <span>
                <i class="el-icon-yonghushuju"></i>用户管理</span>
            </template>
            <el-menu-item index="/Admin/UserManager">用户列表</el-menu-item>
            <el-menu-item index="4-2">管理员管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div>
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="adminavatar">
            <el-dropdown>
              <el-avatar :src="user.avatar" style="border-radius: 50%;"></el-avatar>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                <!--  native可以让自定义组件看作内置dom元素，继而自动绑定事件-->
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script>
// 引入axios
import axios from 'axios';
//引入request统一返回对象
import request from '@/utils/request';
import { Upload } from 'element-ui';

export default {
  name: "Admin_Home",
  // 组件的JavaScript代码
  data() {
    return {
      users: [],
      user: JSON.parse(localStorage.getItem('Moive_user') || '{}'),
      fileList: []
    }
  },
  mounted() {

  },
  methods: {
    handleFileUpload(response, file, fileList) {
      this.fileList = fileList;
    },
    logout() {   // 退出登录
      // 清除当前token和用户信息
      localStorage.removeItem('Moive_user');
      this.$router.push('/login');

    }
  }
}

</script>

<style>
.el_aside {
  width: 250px !important;
  height: 100vh;
  background-color: #ecefff;
  border-radius: 10px;

  margin-left: 2px;
}

.aside_logo {
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo_image {
  height: 60px;
  width: 200px;
}

.el-icon-dianying,
.el-icon-dianyingpiao,
.el-icon-yonghushuju,
.el-icon-zhuye {
  font-size: 20px;
  padding-right: 20px;
}

.el-submenu__title {
  padding-left: 40px !important;
}

.el-menu-item {
  padding-left: 40px !important;
}

.el-menu--inline {
  background-color: #e7ebff;
  padding-left: 35px;
}

.el-submenu__title:hover {
  color: #8284ff;
}

.el-menu-item:hover {
  color: #8284ff;
}

.el-aside {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  display: flex;
  align-items: center;
}

.el-main {
  padding: 5px 0px 0px 5px;
}



.adminavatar {
  flex: 1;
  width: 0;
  display: flex;
  align-content: center;
  justify-content: flex-end;
  margin-right: 30px;
}
</style>