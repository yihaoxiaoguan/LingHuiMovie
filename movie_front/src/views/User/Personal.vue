<template>
  <div>
    <el-card >
        <el-container>
            <el-aside>
      <el-menu style="background-color:#fbfbfb;">
        <div style="padding: 55px 10px 25px 10px;">
          <img :src="user.avatar" style="width: 100px; height: 100px; border-radius: 50%;"/>
          <div style="padding-top: 15px;"><span>{{ user.username }}</span></div>
        </div>
        <el-menu-item @click="$router.push('/Personal/PerInfo')">我的信息</el-menu-item>
        <el-menu-item @click="$router.push('/Personal/PerUpdate')">完善信息</el-menu-item>
        <el-menu-item @click="$router.push('/Personal/PerPassword')">修改密码</el-menu-item>
        <el-menu-item @click="$router.push('/Personal/PerOrder')">我的订单</el-menu-item>
        
      </el-menu>
    </el-aside>
  <el-main>
    <router-view  @update:user="updateUser"></router-view>
    </el-main>
        </el-container>
    </el-card>
  </div>
</template>

<script>
export default {
    name: "Personal",
    data(){
        return{
          user: JSON.parse(localStorage.getItem('Moive_user') || '{}') //从浏览器的本地存储中获取token（用户信息）
        }
    },
    methods: {
      updateUser(user){
        this.user = JSON.parse(JSON.stringify(user)) ;
    }
  }
}
</script>

<style>
.el-aside{
    height: 80vh;
    background-color:#fbfbfb;
    border-radius: 12px;
    text-align: center;
    font-size: 16px;
    font-weight: 600;
    line-height: 20px;
    cursor: pointer;
    outline: none;
    border-right: 1px solid #ccc
}

</style>