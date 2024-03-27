<template>
  <div class="cinema_info">
    <div class="cinema_list">
      <div style="display: flex; padding: 10px 10px 10px 30px;">
        <div>
          <img :src="cinema.photo" style="width: 100%; height: 250px;">
        </div>
        <div style="padding-left: 50px;">
          <div>
            <h2>{{ cinema.name }}</h2>
          </div>
          <div>
            <h5>电话：{{ cinema.phone }}</h5>
          </div>
          <div>
            <h5>地址：{{ cinema.address }}</h5>
          </div>
        </div>

      </div>
    </div>
    <div class="cinema_film">
      <div class="cinema_filmList">
        <div v-for="item in filmList" :key="item.movieId" class="film-item" @click="selectMovie(item)"
          :class="{ 'film-item-selected': item === selectedMovie }">
          <img :src="item.poster"
            :style="{ width: '133px', height: '186px', transform: item === selectedMovie ? 'scale(1.2)' : 'scale(1)' }">
        </div>
      </div>
      <div v-if="selectedMovie" class="movie-info">
        <div>
          <h2 style="
    font-size: 26px;
    font-weight: 400;
    color: #333;">{{ selectedMovie.movieName }}</h2>
        </div>
        <div>
          <span class="film_other_info">时长: {{ selectedMovie.duration }} 分钟</span>
          <span class="film_other_info">类型：{{ selectedMovie.type }}</span>
          <span class="film_other_info">主演：{{ selectedMovie.actors }}</span>
          <el-divider></el-divider>
        </div>
      </div>
    </div>
    <div>
      <div style="font-size: 14px; padding: 0 0 20px 0;">
        <span>放映日期：</span>
        <el-button class="select_date" @click="selectedDate = 'today'">{{ formatDate(new Date()) }}</el-button>
        <el-button class="select_date" @click="selectedDate = 'tomorrow'">{{ formatDate(new Date(), 1) }}</el-button>
        <el-button class="select_date" @click="selectedDate = 'afterTomorrow'">{{ formatDate(new Date(), 2)
          }}</el-button>
      </div>
      <el-table v-if="filteredShowtimeList.length" :data="filteredShowtimeList" style="width: 100%">

        <el-table-column prop="startTime" label="放映时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="hallName" label="放映厅"></el-table-column>
    <el-table-column prop="screenType" label="放映版本"></el-table-column>
        <el-table-column prop="price" label="票价(元)"></el-table-column>
        <el-table-column label="选座购票">
    <template v-slot="scope">
      <el-button type="primary" plain @click="handleSelectSeat(scope.row)"
                style="background-color: #f03d37; border-radius: 30px;color: #fff;">
        选座购票
      </el-button>
    </template>
  </el-table-column>
      </el-table>
      <div v-else style="text-align: center; padding: 20px;">
        <el-empty description="暂无场次"></el-empty>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cinema_Info",
  data() {
    return {
      filmList: [],
      selectedMovie: null,
      showtimeList: [],
      cinemaId: null,
      selectedDate: 'today',
      cinema: {},
    };
  },
  computed: {
    // 添加一个计算属性来筛选当前选择日期的放映场次
    filteredShowtimeList() {
      if (this.selectedDate === 'today') {
        const today = new Date();
        return this.showtimeList.filter(showtime => {
          const showtimeDate = new Date(showtime.date);
          return showtimeDate.getFullYear() === today.getFullYear() &&
            showtimeDate.getMonth() === today.getMonth() &&
            showtimeDate.getDate() === today.getDate();
        });
      } else if (this.selectedDate === 'tomorrow') {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        return this.showtimeList.filter(showtime => {
          const showtimeDate = new Date(showtime.date);
          return showtimeDate.getFullYear() === tomorrow.getFullYear() &&
            showtimeDate.getMonth() === tomorrow.getMonth() &&
            showtimeDate.getDate() === tomorrow.getDate();
        });
      } else if (this.selectedDate === 'afterTomorrow') {
        const afterTomorrow = new Date();
        afterTomorrow.setDate(afterTomorrow.getDate() + 2);
        return this.showtimeList.filter(showtime => {
          const showtimeDate = new Date(showtime.date);
          return showtimeDate.getFullYear() === afterTomorrow.getFullYear() &&
            showtimeDate.getMonth() === afterTomorrow.getMonth() &&
            showtimeDate.getDate() === afterTomorrow.getDate();
        });
      } else {
        return [];
      }
    },
  },
  created() {
    this.cinemaId = this.$route.params.id;
  },
  methods: {
    getMovieInfo() {
      this.$request.get('/film/all').then(res => {
        this.filmList = res.data;
        // 如果filmList有数据，则选择第一个电影并获取其放映场次
        if (this.filmList.length > 0) {
          this.selectMovie(this.filmList[0]);
        }
      }).catch(error => {
        console.error('获取电影信息失败：', error);
      });
    },
    getshowtimeList() {
      if (this.selectedMovie) {
        this.$request.get('/showtime/selectByCAM', { params: { movieId: this.selectedMovie.movieId, cinemaId: this.cinemaId } }).then(res => {
          this.showtimeList = res; // 注意这里应该使用res.data，因为通常API返回的是一个对象，其中包含数据字段data
        }).catch(error => {
          console.error('获取场次信息失败：', error);
        });
      }
    },
    handleSelectSeat(row) {
      // 从当前行数据中获取 showtimeId
      const showtimeId = row.showtimeId;
      // 跳转到选座页面，并传递 showtimeId 和 movieId 参数
      this.$router.push('/showtime='+ showtimeId +'&movieId='+this.selectedMovie.movieId +'&cinemaId='+ this.cinemaId);
    },
    getCinema(){
      this.$request.get(`/cinema/selectById/${this.cinemaId}`).then(res => {
        this.cinema = res; 
      })
    },
    selectMovie(movie) {
      this.selectedMovie = movie;
      this.getshowtimeList(); // 在选择电影后直接获取其放映场次
    },
    formatDate(date, daysToAdd = 0, includeDayText = false) {
      const formattedDate = new Date(date);
      formattedDate.setDate(formattedDate.getDate() + daysToAdd);

      if (includeDayText) {
        const dayOfWeek = ['今天', '明天', '后天'];
        return dayOfWeek[formattedDate.getDay()];
      } else {
        return `${formattedDate.getMonth() + 1}月${formattedDate.getDate()}日`;
      }
    },
  },
  mounted() {
    this.getMovieInfo();
    this.getCinema();
  }
};
</script>

<style>
.cinema_info {
  padding: 0 150px;
}

.cinema_film {
  width: 100%;
  background-color: #fff
}

.cinema_filmList {
  height: 280px;
  padding: 22px 5px;
  display: flex;
  flex-wrap: nowrap;
  box-shadow: inset 0 0 100px 80px #ededed;
  justify-content: space-around;
  align-content: center !important;
}

.film-item {
  transition: transform 0.3s ease;
}

.film_other_info {
  padding: 0 35px 0 0;
  color: #151515;
  font-size: 14px;

}

.el-table td.el-table__cell,
.el-table th.el-table__cell.is-leaf {
  border-bottom: 0;
}

.film-item-selected img {
  transform: scale(1.2);
  /* 放大20% */
}

</style>