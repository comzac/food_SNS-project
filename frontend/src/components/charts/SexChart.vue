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
            backgroundColor: "#f87979",
            barThickness: 60,
            datalabels: {
              color: "white",
              font: {
                weight: "bold",
                size: 20,
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
              backgroundColor: "#f87979",
              barThickness: 60,
              datalabels: {
                color: "white",
                font: {
                  weight: "bold",
                  size: 20,
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
  mounted() {
    this.renderChart(this.chartdata, this.options);
  },
};
</script>

<style></style>
