<script>
const horizonalLinePlugin = {
  id: "horizontalLine",
  afterDraw: function(chartInstance) {
    var yValue;
    var yScale = chartInstance.scales["y-axis-0"];
    var canvas = chartInstance.chart;
    var ctx = canvas.ctx;
    var index;
    var line;
    var style;

    if (chartInstance.options.horizontalLine) {
      for (
        index = 0;
        index < chartInstance.options.horizontalLine.length;
        index++
      ) {
        line = chartInstance.options.horizontalLine[index];

        if (!line.style) {
          style = "#ffffff";
        } else {
          style = line.style;
        }

        if (line.y) {
          yValue = yScale.getPixelForValue(line.y);
        } else {
          yValue = 0;
        }
        ctx.lineWidth = 3;

        if (yValue) {
          window.chart = chartInstance;
          ctx.beginPath();
          ctx.moveTo(0, yValue);
          ctx.lineTo(canvas.width, yValue);
          ctx.strokeStyle = style;
          ctx.stroke();
        }

        if (line.text) {
          ctx.fillStyle = style;
          ctx.fillText(line.text, 0, yValue + ctx.lineWidth);
        }
      }
      return;
    }
  },
};

import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: {
    datum: Object,
  },
  beforeMount() {
    this.addPlugin(horizonalLinePlugin);
  },
  mounted() {
    this.renderChart(this.datum, {
      legend: {
        position: "bottom",
      },
      showTooltips: false,
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        xAxes: [
          {
            stacked: true,
            categoryPercentage: 0.8,
            barPercentage: 1,
          },
        ],
        yAxes: [
          {
            stacked: true,
            display: false,
          },
        ],
      },
    });
  },
};
</script>
