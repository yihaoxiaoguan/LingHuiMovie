<template>
  <div>
    <div class="filmbackground">
    </div>
    <div class="filmposter">
      <img style="width: 240px; height: 330px;" :src="MovieInfo.poster">
    </div>
    <div class="filmInfo">
      <div class="filmName">{{ MovieInfo.movieName }}</div>
      <div class="filmother">
        <div>{{ MovieInfo.type }}</div>
        <div style="margin: 10px 0;">{{ MovieInfo.region }} <span> / </span> {{ MovieInfo.duration }}分钟</div>
        <div>{{ MovieInfo.releaseTime }}上映</div>
        <div style="padding-top: 50px">
          <div class="scorebtn">
            <el-button @click="openComment" type="danger" style="width: 120px;
    height: 36px;letter-spacing: 2px;">
              <i style="padding-right: 5px;font-size: 15px" class="el-icon-star-on"></i>评分
            </el-button>
          </div>
          <div>
            <router-link style="margin-right: 20px" :to="'/film/ticket?fid=' + id">
              <el-button type="danger" style="width: 240px;
    height: 41px;letter-spacing: 2px">
                <i style="padding-right: 5px;font-size: 15px" class="el-icon-s-finance"></i>特惠购票
              </el-button>
            </router-link>
          </div>

        </div>

      </div>

    </div>
    <div class="filmdesc">
      <div style="margin: 10px 0 ;">
        <i style="font-size: 12px;">口碑评分</i>
        <div style="font-size: 30px;color: #ff9900;">{{ MovieInfo.score }} 
          <el-rate v-model="value" style="float: right; padding-left: 50px;" disabled></el-rate>

        </div>
      </div>
      <div style="margin: 10px 0 ;">
        <i style="font-size: 12px;">累计票房</i>
        <div style="font-size: 30px;">{{ MovieInfo.boxOffice }}</div>
      </div>
    </div>
    <div class="filmin">
      <div style="font-size: 18px;margin: 20px 0;">
        <h3>剧情简介</h3>
      </div>
      <div style="font-size: 15px;">
        {{ MovieInfo.introduction }}
      </div>
      <div style="font-size: 18px;margin: 20px 0;">
        <h3>演职人员</h3>
      </div>
      <div style="font-size: 15px;">
        <div>
          <span><i>导演：</i>{{ MovieInfo.director }}</span>
        </div>
        <div style="padding-top: 10px;">
          <span><i>演员：</i>{{ MovieInfo.actors }}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>

export default {
  name: 'MovieInfo',
  data() {
    return {
      MovieInfo: {// 电影信息对象，包含标题、描述、海报等属性，根据实际情况定义
      },
      value: 4.5,
    }
  },


  mounted() { // 组件挂载后调用，可以在这里获取电影信息并更新到Movie对象中。
    // 获取电影信息的逻辑，可以使用axios或其他HTTP库发送请求到电影API。

    this.updata();



  },
  methods: {  // 组件的方法，可以在这里定义一些操作函数。
    updata() {
      this.$request.get('/film/' + this.$route.params.id).then(res => { // 假设API路径是/api/movie/:id，:id是动态路由参数。
        this.MovieInfo = res; // 更新Movie对象。// 更新评分组件的值。
      }).catch(error => { // 处理错误。
        console.error('获取电影信息失败：', error);
      });
    }
  }
}
</script>

<style>
.filmbackground {
  position: relative;
  width: 100%;
  height: 380px;
  background-color: #0093E9;
  background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
  margin-top: 12px;
}

.filmposter {
  border: 4px solid #fff;
  border-radius: 4px;
  background: #FFFFFF;
  margin-right: 100px;
  margin-left: 190px;
  position: absolute;
  overflow: hidden;
  top: 160px;
  z-index: 999;
}

.filmInfo {
  width: 260px;
  margin-left: 480px;
  position: absolute;
  overflow: hidden;
  top: 160px;
  z-index: 999;
}

.filmName {
  font-size: 30px;
  line-height: 32px;
  font-weight: 700;
  color: #fff;
}

.filmother {
  font-size: 16px;
  line-height: 20px;
  margin-top: 40px;
  color: #fff;
  font-size: 14px;
}

.scorebtn {
  margin: 10px 10px 10px 0;
}

.filmdesc {
  position: absolute;
  left: 820px;
  /* 将元素靠右显示 */
  top: 300px;
  /* 你需要设置的 top 值 */
  /* 例如，top: 50px; */
  z-index: 999;
  color: #fff;
}

.filmin {
  width: 800px;
  margin: 60px 0 0 200px;
}
</style>