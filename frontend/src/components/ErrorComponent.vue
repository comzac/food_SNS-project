<template>
  <h1 class="text-center mt-10">
    Error
    <div class="my-2">
      <v-btn small @click="moveToBack">뒤로가기</v-btn>
    </div>
  </h1>
</template>

<script>
export default {
  methods: {
    moveToBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style></style>
