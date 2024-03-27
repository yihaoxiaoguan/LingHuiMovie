<template>
    <div class="movies">
      <div class="movies_box_select">
          <div class="tags">
              <span class="tags-title">类型 :</span>
              <span 
                  v-for="(item, index) in typeList" 
                  :key="index" 
                  :class="selectType===item ? 'active' : ''" 
                  @click="handleTypeSelect(item)"
                  >
              <router-link class="tags_select" :to=" '/movies?region='+ selectRegion + '&type=' + item ">{{ item }}</router-link>
            
            </span >
          </div>
          <div class="tags">
              <span class="tags-title">地区 :</span>

            <span v-for="(item,index) in regionList" 
            :key="index"
            :class="selectRegion===item ? 'active' : ''"
                @click="handleRegionSelect(item)">
                <router-link class="tags_select" :to=" '/movies?region='+ item + '&type=' + selectType ">{{ item }}</router-link>
            </span>
          </div>
      </div>
      <div class="movies_content">
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
  </template>
<script>
export default {
name: 'Movies',
data() {
  return {
    selectType: '全部',
    selectRegion: '全部',
    typeList: ["全部", "家庭", "惊悚", "科幻", "爱情", "动作", "喜剧", "恐怖", "悬疑", "犯罪", "冒险", "战争", "历史", "武侠", "传记", "古装", "其他"],
    regionList: ["全部", "内地", "香港", "台湾", "美国", "韩国", "日本", "泰国", "印度", "法国", "英国", "德国", "其他"],
    filmList: [],
  }
},

mounted() {
    
      this.$request.get('/film/all').then(res => { // 假设API路径是/api/movie/:id，:id是动态路由参数。
        console.log(res.data);
        this.filmList = res.data; // 更新Movie对象。// 更新评分组件的值。
      }).catch(error => { // 处理错误。
        console.error('获取电影信息失败：', error);
      });
    },
  methods: {

  handleTypeSelect(item) {
      this.selectType = item;
      this.reloadFilmList()
    },

    handleRegionSelect(item) {
      this.selectRegion = item;
      this.reloadFilmList()
    },

    reloadFilmList() {
      this.$request.get('/film/selectByRAT',{ params: { region: this.selectRegion, type: this.selectType } }).then(res => {
        this.filmList = res
        console.log(this.filmList);

    }).catch(error => {
        console.error('获取电影列表失败：', error);
      });
      
    },

}
}
</script>

<style>
.movies{
    margin: 0 160px;
}
.movies_box_select{
    margin: 20px 0 0 0 ;
    height: 100px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.tags-title {
    height: 24px;
    line-height: 24px;
    float: left;
    color: #999;
    font-size: 14px;
    padding: 0 10px 0 0;
} 
.tags {
    width: 100%;
    height: 50px;
    padding: 0 40px;
    line-height: 24px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
}
.tags_select {
    padding: 0 10px;
    color: black;
    border: 1px solid transparent; /* 默认边框透明 */
    transition: all 0.3s ease; /* 添加过渡效果 */
    text-decoration: none;
}
.active {
    background-color: #f03d37; /* 被选中时的背景色 */
    border-radius: 40%;
    color: #fff; /* 被选中时的文字颜色 */
}

.movies_content{
    margin: 30px 0 0 0 ;
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
</style>