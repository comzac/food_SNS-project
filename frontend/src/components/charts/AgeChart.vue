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
          },
        },
      ];
      if (this.ageData.age10) {
        dataset[0].backgroundColor.push("#41B883");
        dataset[0].data.push(this.ageData.age10);
      }
      if (this.ageData.age20) {
        dataset[0].backgroundColor.push("#E46651");
        dataset[0].data.push(this.ageData.age20);
      }
      if (this.ageData.age30) {
        dataset[0].backgroundColor.push("#00D8FF");
        dataset[0].data.push(this.ageData.age30);
      }
      if (this.ageData.age40) {
        dataset[0].backgroundColor.push("#DD1B16");
        dataset[0].data.push(this.ageData.age40);
      }
      if (this.ageData.age50) {
        dataset[0].backgroundColor.push("#ff66ff");
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

<style></style>
