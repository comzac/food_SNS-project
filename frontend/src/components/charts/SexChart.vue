<script>
import { Bar } from "vue-chartjs";
export default {
  extends: Bar,
  props: {
    sexData: Object,
  },
  data() {
    return {
      chartdata: {
        labels: ["남", "여"],
        datasets: [
          {
            label: "좋아요",
            backgroundColor: ["#6ba292", "#EDCA6B"],
            barThickness: 60,
            datalabels: {
              color: "white",
              font: {
                weight: "bold",
                size: 20,
              },
              formatter: function(value, context) {
                let likeCount = context.dataset.data.reduce(
                  (acc, cur) => acc + cur,
                  0
                );
                // console.log(likeCount, context);
                return Math.round((value / likeCount) * 100) + "%";
              },
            },
            data: [this.sexData.male, this.sexData.female],
          },
        ],
      },

      options: {
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                stepSize: 1,
                display: false,
              },
              gridLines: {
                display: false,
              },
            },
          ],
          xAxes: [
            {
              gridLines: {
                display: false,
              },
            },
          ],
        },
        legend: {
          display: false,
        },
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  watch: {
    sexData: {
      deep: true,
      handler(newData, oldData) {
        oldData;
        this.chartdata = {
          labels: ["남", "여"],
          datasets: [
            {
              label: "좋아요",
              backgroundColor: ["#6ba292", "#EDCA6B"],
              barThickness: 60,
              datalabels: {
                color: "white",
                font: {
                  weight: "bold",
                  size: 20,
                },
                formatter: function(value, context) {
                  let likeCount = context.dataset.data.reduce(
                    (acc, cur) => acc + cur,
                    0
                  );
                  // console.log(likeCount, context);
                  return Math.round((value / likeCount) * 100) + "%";
                },
              },
              data: [newData.male, newData.female],
            },
          ],
        };
        this.renderChart(this.chartdata, this.options);
      },
    },
  },
  computed: {
    likeCount() {
      return this.sexData.male + this.sexData.female;
    },
  },
  mounted() {
    this.renderChart(this.chartdata, this.options);
  },
};
</script>

<style scoped>
#bar-chart {
  padding: 20px;
}
</style>
