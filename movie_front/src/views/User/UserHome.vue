<template>
  <div>
  <el-container>
    <el-header>
  <div class="user-header">
    <el-menu mode="horizontal" class="user-header-menu">
      <el-menu-item class="logo">
        <img src="../../assets/image/logo.png" alt="logo">
      </el-menu-item>
      <el-menu-item>
        <el-link href="/home">首页</el-link>
      </el-menu-item>
      <el-menu-item>
        <el-link href="/movies?region=全部&type=全部">电影</el-link>
      </el-menu-item>
      <el-menu-item>
        <el-link href="/cinema?area=全部&brand=全部">影院</el-link>
      </el-menu-item>
      <div >
        <el-autocomplete
        class="header-search"
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入电影名"
            @select="handleSelect"
        />
      </div>
      <div class="adminavatar">
        <el-dropdown>
          <el-avatar :src="user.avatar" style="border-radius: 50%;"></el-avatar>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push('/Personal')">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      
    </el-menu>
  </div>
  </el-header>
  <el-main>
    <router-view></router-view>
  </el-main>
  </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('Moive_user') || '{}') ,//从浏览器的本地存储中获取token（用户信息）
      searchList: [],
      state: '',
      timeout: null,
  }
},
  methods: {
    logout() {   // 退出登录   // 清除当前token和用户信息
      localStorage.removeItem('Moive_user');
      this.$router.push('/login');
    },
    querySearchAsync(queryString, cb) {
      if (queryString) {
        this.$request.get('/film/findByName/'+queryString).then(res => {
          setTimeout(() => {
            this.searchList = res
            cb(this.loadSearchList())
          }, 600)
        })
      }
    },

    loadSearchList() {
      let arr = [];
      if (this.searchList){
      for (let i = 0; i < this.searchList.length; i++) {
        arr.push({
          id: this.searchList[i].movieId,
          value: this.searchList[i].movieName,
        })
      }
    }
      return arr
    },

    handleSelect(item) {
      this.$router.push('/MovieInfo/' + item.id)
    },
  }
}

</script>

<style scoped>
.el-header{
  padding: 0;
  border: 1px solid #EBEEF5;
}
.user-header {
  height: 80px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

.user-header-menu {
  width: 100%;
  padding: 0 80px 0 130px;
  display: flex;
  align-items: center;
  border: none;
}
.el-menu-item{
  font-size: 20px;
  line-height: 80px;  /* 设置行高 */
  height: 80px;
  color: #1d1e1f;
}
.el-link{
  font-weight: 500;
    font-size: 18px;
}
.logo img {
  height: 65px;
  width: 210px;
}

.header-search{
  width: 300px;
  margin-left: 60px;
  border-radius: 50%;
}
.el-input__inner {
  border-radius: 50px;
}

.adminavatar {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-right: 50px;
}
</style>