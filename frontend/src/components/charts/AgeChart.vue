<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  props: {
    ageData: Object,
  },
  computed: {
    labels() {
      const label = [];
      if (this.ageData.age10) label.push("10대");
      if (this.ageData.age20) label.push("20대");
      if (this.ageData.age30) label.push("30대");
      if (this.ageData.age40) label.push("40대");
      if (this.ageData.age50) label.push("50대 이상");
      return label;
    },
    datasets() {
      const dataset = [
        {
          backgroundColor: [],
          data: [],
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
        },
      ];
      if (this.ageData.age10) {
        dataset[0].backgroundColor.push("#38908f");
        dataset[0].data.push(this.ageData.age10);
      }
      if (this.ageData.age20) {
        dataset[0].backgroundColor.push("#82bbb0");
        dataset[0].data.push(this.ageData.age20);
      }
      if (this.ageData.age30) {
        dataset[0].backgroundColor.push("#5e96ae");
        dataset[0].data.push(this.ageData.age30);
      }
      if (this.ageData.age40) {
        dataset[0].backgroundColor.push("#dd9f93");
        dataset[0].data.push(this.ageData.age40);
      }
      if (this.ageData.age50) {
        dataset[0].backgroundColor.push("#e08963");
        dataset[0].data.push(this.ageData.age50);
      }
      return dataset;
    },
  },
  watch: {
    ageData: {
      deep: true,
      handler(newData, oldData) {
        oldData;
        this.renderChart(
          {
            labels: this.labels,
            datasets: this.datasets,
          },
          { responsive: true, maintainAspectRatio: false }
        );
      },
    },
  },
  mounted() {
    this.renderChart(
      {
        labels: this.labels,
        datasets: this.datasets,
      },
      { responsive: true, maintainAspectRatio: false }
    );
  },
};
</script>

<style scoped>
#pie-chart {
  padding: 20px;
}
</style>
