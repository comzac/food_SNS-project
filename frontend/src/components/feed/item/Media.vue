<template>
  <div>
    <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
    <v-window v-model="i2" @dblclick.native="$emit('like-unlike')" continuous>
      <v-window-item v-for="(file, i) in dbFiles" :key="i">
        <video
          v-if="file.type === 'video/mp4'"
          :src="`data:${file.type};base64,${file.data}`"
          type="video/mp4"
          class="my-auto"
          width="100%"
          autoplay
          loop
          muted
        ></video>
        <v-img
          v-if="file.type !== 'video/mp4'"
          :src="`data:${file.type};base64,${file.data}`"
          width="100%"
        ></v-img>
      </v-window-item>
    </v-window>

    <v-card-actions class="justify-space-between">
      <v-btn text @click="prev" color="#ff6666">
        <v-icon>mdi-chevron-double-left</v-icon>
      </v-btn>
      <v-item-group v-model="i2" class="text-center" mandatory>
        <v-item v-for="n in dbFiles.length" :key="n" v-slot:default="{ active, toggle }">
          <v-btn :input-value="active" icon @click="toggle" color="#ff6666">
            <v-icon>mdi-record</v-icon>
          </v-btn>
        </v-item>
      </v-item-group>
      <v-btn text @click="next" color="#ff6666">
        <v-icon>mdi-chevron-double-right</v-icon>
      </v-btn>
    </v-card-actions>
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
    },
    next() {
      this.i2 = this.i2 + 1 === this.dbFiles.length ? 0 : this.i2 + 1;
    },
    prev() {
      this.i2 = this.i2 - 1 < 0 ? this.dbFiles.length - 1 : this.i2 - 1;
    },
  },
};
</script>

<style></style>
