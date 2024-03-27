<script>
import axios from 'axios';

export default {
  data() {
    return {
      movies: []
    };
  },
  mounted() {
    axios.get('https://m.maoyan.com/ajax/movieOnInfoList')
      .then(response => {
        this.movies = response.data.list;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>