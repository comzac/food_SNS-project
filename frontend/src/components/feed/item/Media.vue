<template>
  <div>
    <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
    <div v-for="(preview, i) in previews" :key="i" id="previews">
      <v-responsive
        v-if="preview.includes('data:video/mp4', 0)"
        v-show="i == i2"
        aspect-ratio="1"
        class="align-center"
      >
        <video
          :id="i"
          :src="preview"
          controls
          type="video/mp4"
          class="my-auto"
        ></video>
      </v-responsive>
      <v-responsive
        v-if="!preview.includes('data:video/mp4', 0)"
        v-show="i == i2"
        aspect-ratio="1"
        class="align-center"
      >
        <v-img :id="i" :src="preview" width="100%"></v-img>
      </v-responsive>
    </div>
    <!-- 슬라이더 -->
    <v-slider
      prepend-icon="mdi-chevron-double-left"
      @click:prepend="i2 > 1 ? (i2 -= 1) : (i2 = 0)"
      append-icon="mdi-chevron-double-right"
      @click:append="
        i2 < previews.length - 1 ? (i2 += 1) : (i2 = previews.length - 1)
      "
      v-if="previews.length > 0"
      v-model="i2"
      :max="previews.length - 1"
      step="1"
      thumb-color="#ff6666"
      thumb-labels="always"
      thumb-size="40"
      id="slider"
    >
      <template v-slot:thumb-label>{{ i2 + 1 }}</template>
    </v-slider>
  </div>
</template>

<script>
export default {
  name: "Media",
  components: {},
  props: {
    dbFiles: Array,
  },
  data() {
    return {
      previews: [],
      i2: 0,
    };
  },
  methods: {
    getMedia(dbFiles) {
      for (let i = 0; i < dbFiles.length; i++) {
        let reader = new FileReader();
        reader.onload = () => {
          this.previews.push(reader.result);
        };
        reader.readAsDataURL(this.dbFiles[i]);
      }
      setTimeout(function() {
        document.getElementById("slider").click();
      }, 500);
    },
  },
  mounted() {
    this.getMedia(this.dbFiles);
  },
};
</script>

<style></style>
