<template>
  <div class="d-flex justify-center">
    <img
      class="liquor1"
      src="@/assets/alchol4.svg"
      width="100%"
      style="position:absolute; left:50%;transform:translateX(-50%);"
    />
    <LiquorChart :datum="datum" class="liquor2 mx-0" />
  </div>
</template>

<script>
import LiquorChart from "@/components/charts/LiquorChart";

export default {
  name: "Liquor",
  props: {
    datum: Object,
  },
  components: {
    LiquorChart,
  },
};
</script>
<style scoped>
.liquor1 {
  width: 295px;
  height: 300px;
}
.liquor2 {
  width: 290px;
  height: 300px;
}
</style>
