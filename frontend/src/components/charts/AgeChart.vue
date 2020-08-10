<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  props: {
    ageData: Object,
  },
  mounted() {
    this.renderChart(
      {
        labels: ["10대", "20대", "30대", "40대", "50대 이상"],
        datasets: [
          {
            backgroundColor: [
              "#41B883",
              "#E46651",
              "#00D8FF",
              "#DD1B16",
              "#ff66ff",
            ],
            data: [
              this.ageData.age10,
              this.ageData.age20,
              this.ageData.age30,
              this.ageData.age40,
              this.ageData.age50,
            ],
          },
        ],
      },
      { responsive: true, maintainAspectRatio: false }
    );
  },
};
</script>

<style></style>
