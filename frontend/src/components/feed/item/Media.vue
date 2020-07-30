<template>
  <div>
    <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
    <div v-for="(file, i) in dbFiles" :key="i" id="files">
      <v-responsive
        v-if="file.type === 'video/mp4'"
        v-show="i == i2"
        aspect-ratio="1"
        class="align-center"
      >
        <video
          :id="i"
          :src="
                `data:${file.type};base64,${file.data}`
              "
          controls
          type="video/mp4"
          class="my-auto"
        ></video>
      </v-responsive>
      <v-responsive
        v-if="file.type !== 'video/mp4'"
        v-show="i == i2"
        aspect-ratio="1"
        class="align-center"
      >
        <v-img
          :id="i"
          :src="
                `data:${file.type};base64,${file.data}`
              "
          width="100%"
        ></v-img>
      </v-responsive>
    </div>
    <!-- 슬라이더 -->
    <v-slider
      prepend-icon="mdi-chevron-double-left"
      @click:prepend="i2 > 1 ? (i2 -= 1) : (i2 = 0)"
      append-icon="mdi-chevron-double-right"
      @click:append="
        i2 < dbFiles.length - 1 ? (i2 += 1) : (i2 = dbfiles.length - 1)
      "
      v-if="dbFiles.length > 0"
      v-model="i2"
      :max="dbFiles.length - 1"
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
      console.log(dbFiles);
      for (let i = 0; i < dbFiles.length; i++) {
        let reader = new FileReader();
        reader.onload = () => {
          console.log(reader.result);
          this.previews.push(reader.result);
        };
        reader.readAsDataURL(this.dbFiles[i]);
      }
      setTimeout(function () {
        document.getElementById("slider").click();
      }, 500);
    },
  },
  created() {
    console.log("updated");
    // this.getMedia(this.dbFiles);
  },
};
</script>

<style></style>
