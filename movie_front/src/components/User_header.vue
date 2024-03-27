<template>
  <div class="user-header">
    <el-menu mode="horizontal" class="user-header-menu">
      <el-menu-item class="logo">
        <img src="../assets/image/logo.png" alt="logo">
      </el-menu-item>
      <el-menu-item>
        <el-link index="/">首页</el-link>
      </el-menu-item>
      <el-menu-item>
        <el-link index="1">电影</el-link>
      </el-menu-item>
      <el-menu-item>
        <el-link index="1">影院</el-link>
      </el-menu-item>
      <el-menu-item class="search" style="margin-left: 200px;">
        <el-input prefix-icon="el-icon-search" placeholder="搜索"></el-input>
      </el-menu-item>
      <div class="adminavatar" style="margin-right: 50px;">
        <el-dropdown>
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
.user-header {
  height: 80px;
  display: flex;
  align-items: center;
}

.user-header-menu {
  width: 100%;
  display: flex;
  align-items: center;
}

.logo img {
  height: 65px;
  width: 210px;
}

.search .el-input {
  width: 300px;
}

.adminavatar {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
</style>