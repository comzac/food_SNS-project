<template>
  <div class="d-flex justify-center">
    <img
      class="gender1"
      src="@/assets/mw5.svg"
      width="100%"
      style="position:absolute; left:50%;transform:translateX(-50%);"
    />
    <SexChart
      :sexData="sexData"
      class="gender2"
      style="background-color: grey;"
    />
  </div>
</template>

<script>
import SexChart from "@/components/charts/SexChart";

export default {
  name: "Gender",
  props: {
    sexData: Object,
  },
  components: {
    SexChart,
  },
};
</script>

<style scoped>
.gender1 {
  width: 310px;
  height: 310px;
}
.gender2 {
  width: 300px;
  height: 300px;
}
</style>
