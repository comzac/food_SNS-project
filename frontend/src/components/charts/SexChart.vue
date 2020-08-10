<script>
import { Bar } from "vue-chartjs";
export default {
  extends: Bar,
  props: {
    sexData: Object,
  },
  data: () => ({
    chartdata: {
      labels: ["남", "여"],
      datasets: [
        {
          label: "좋아요",
          backgroundColor: "#f87979",
          barThickness: 60,
          data: [20, 30],
        },
      ],
    },
    // options: {
    //   responsive: true,
    //   maintainAspectRatio: false,
    // },
    options: {
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
              stepSize: 1,
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
        display: true,
      },
      responsive: true,
      maintainAspectRatio: false,
    },
  }),

  mounted() {
    this.renderChart(this.chartdata, this.options);
  },
};
</script>

<style></style>
