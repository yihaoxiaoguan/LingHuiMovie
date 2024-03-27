<template>
  <div class="moiveHome">
    <div class="moiveCarousel">
      <el-carousel :interval="4000" type="card" height="200px" indicator-position="outside">
        <el-carousel-item v-for="item in filmList" :key="item.movieId">
          <img :src="item.poster" style="width: 100%; height: 200px;" :alt="item.movieName">
          <h3 class="medium">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="movieRecommended">
      <div class="movieOnIn" style="width: 785px;height: 670px;">
        <div class="movieOnIn_header" style="display: flex; padding-bottom: 12px;">
          <div style="flex: 1;">
            <span style="font-size: 35px; color: red;">
              正在热映
            </span>
          </div>
          <div style="display: flex; justify-content: flex-end; font-size: 20px; padding: 10px 48px 0 0; ">
            <span>全部</span>
            <span class="el-icon-arrow-right" style="padding-top: 3px;"></span>
          </div>
        </div>
        <div class="movieOnIn_content">
          <div v-for="item in filmList" :key="item.movieId" class="movie-item">
            <router-link :to="'/MovieInfo/' + item.movieId">
            <div class="movie-poster">
              <div class="movie-overlay">
                <div class="movie-title">{{ item.movieName }}</div>
                <div class="movie-score">{{ item.score }}</div>
              </div>
              <img :src="item.poster" style="width: 160px; height: 220px;">
            </div>
            </router-link>
            <router-link :to="'/cinemas_movieId=' + item.movieId">
            <el-button class="Ticket_btn" style="width: 160px; height: 39px;" type="danger" plain>购票</el-button>
          </router-link>
          </div>
        </div>
      </div>
      <div class="Leaderboard">
    <div class="Leaderboard_title" style="font-size: 30px; color:#ef4238;">热门榜单</div>
    <div class="Leaderboard_list">
      <div v-for="(item, index) in sortedFilmList" :key="item.movieId" class="Leaderboard_item">
        <router-link :to="'/MovieInfo/' + item.movieId" style="text-decoration: none;">
          <div class="Leaderboard_box">
            <span class="Leaderboard_rank">{{ index + 1 }}</span>
            <span class="Leaderboard_name">{{ item.movieName }}</span>
            <span class="Leaderboard_boxoffice" style="color: #ef4238;">{{ item.boxOffice }}亿</span>
          </div>
        </router-link>
      </div>
    </div>
  </div>
  </div>
</div>


</template>

<script>

export default {
  data() {
    return {
      filmList: [], // 电影列表数据。
    }
  },
  mounted() {
    this.getMovie(); // 调用方法获取电影信息。
  },
  methods: {
    getMovie() {
      this.$request.get('/film/all').then(res => { // 假设API路径是/api/movie/:id，:id是动态路由参数。
        console.log(res.data);
        this.filmList = res.data; // 更新Movie对象。// 更新评分组件的值。
      }).catch(error => { // 处理错误。
        console.error('获取电影信息失败：', error);
      });
    }
  },
  computed: {
    sortedFilmList() {// 按照 boxOffice 降序排序
      return this.filmList.sort((a, b) => b.boxOffice - a.boxOffice);
    },
  },
}
</script>

<style>
.moiveHome {
  padding: 50px;
  display: flex;
  flex-direction: column;
}

.movieRecommended {
  padding: 50px 50px 10px 130px;
  display: flex;
}

.movie-item {
  position: relative; /* 使得子元素可以相对于它进行定位 */
  width: 160px; /* 设置电影项目的宽度 */
  margin: 0 30px 20px 0; /* 如果需要的话，添加一些间距 */
  display: inline-block; /* 设置为行内块元素，以便于设置宽度 */
}

.movie-poster {
  position: relative; /* 为绝对定位的覆盖层提供上下文 */
  width: 100%; /* 宽度设置为父元素的100% */
  height: 0; /* 高度设置为0，通过padding来设置实际高度 */
  padding-bottom: 137.5%; /* 保持16:9的比例，即高度为宽度的137.5% */
  overflow: hidden; /* 隐藏图片的超出部分 */

}

.movie-overlay {
  position: absolute;
  bottom: 0; /* 定位到海报的底部 */
  width: 100%; /* 宽度与海报相同 */
  padding: 10px; /* 添加内边距以提供与边缘的距离 */
  color: white; /* 文本颜色为白色 */
  display: flex; /* 使电影名称和评分分别靠左和靠右 */
  align-items: center; /* 垂直居中电影名称和评分 */
}
.movie-score {
  color:#ff9900;

}

.movie-title {
  z-index: 1; /* 确保标题显示在海报上方 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 文本阴影 */
  text-overflow: ellipsis;
}

.Leaderboard{
  width: 360px;
}
.Leaderboard_list{
  padding: 20px 0 0 3px;
}

.Leaderboard_item{
  height: 60px;
  display: block;
}
.Leaderboard_item:hover {
  background-color: #f3f3f3; /* 浅灰色背景 */
}
.Leaderboard_box {
  height: 60px;
  padding: 10px;
  display: flex;
  align-items: center;
  font-size: 16px;
  color: #333;
}
.Leaderboard_rank {
  /* 你可以添加一些样式来突出显示排名，比如不同的颜色或字体大小 */
  font-weight: bold; 
  color: #fb3b3b;/* 例如，使用粗体来突出排名 */
}
.Leaderboard_name {
  display: flex;
  flex: 1;
  padding-left: 20px;
}
.Leaderboard_boxoffice{
  display: flex;
  justify-content: flex-end!important;
}
</style>