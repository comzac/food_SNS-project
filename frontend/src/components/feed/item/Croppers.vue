<template>
  <div>
    <input v-show="false" ref="input" type="file" name="image" accept="image/*" @change="setImage" />

    <section v-show="!cropped || profile" class="cropper-area">
      <div class="img-cropper">
        <vue-cropper
          ref="cropper"
          :viewMode="3"
          :autoCropArea="1"
          :cropBoxMovable="false"
          :aspect-ratio="1"
          :src="imgSrc"
          :minContainerWidth="350"
          :minContainerHeight="350"
          :highlight="false"
          dragMode="move"
          style="aspect-ratio: 1;"
        />
      </div>
    </section>
    <section v-show="cropped" class="preview-area" v-if="!profile">
      <div class="cropped-image">
        <img
          v-show="cropImg"
          :src="cropImg"
          alt="Cropped Image"
          style="display:
          block; width: 100%; max-width:100%;"
        />
      </div>
      <!-- <div v-else class="crop-placeholder" /> -->
    </section>
    <div class="actions">
      <v-btn v-if="!cropped || profile" role="button" @click.prevent="cropImage">수정</v-btn>
      <v-btn v-if="cropped" role="button" @click.prevent="reset">다시</v-btn>
    </div>
  </div>
</template>

<script>
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";

export default {
  components: {
    VueCropper,
  },
  props: {
    imgSrc: String,
    profile: Boolean,
  },
  data() {
    return {
      cropImg: "",
      data: null,
      cropped: false,
    };
  },
  methods: {
    cropImage() {
      // get image data for post processing, e.g. upload or setting image src
      this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
      this.cropped = true;
      this.getData();
      this.$emit("set-number");
      this.$emit("set-data", this.data);
      this.$emit("set-image", this.cropImg);
    },
    getData() {
      this.data = JSON.stringify(this.$refs.cropper.getData(), null, 4);
    },
    reset() {
      this.$refs.cropper.reset();
      this.cropped = false;
      this.$emit("set-number");
      this.$emit("set-data", null);
      this.$emit("set-image", this.imgSrc);
    },
    setImage(e) {
      const file = e.target.files[0];
      if (file.type.indexOf("image/") === -1) {
        alert("Please select an image file");
        return;
      }
      if (typeof FileReader === "function") {
        const reader = new FileReader();
        reader.onload = (event) => {
          this.imgSrc = event.target.result;
          // rebuild cropperjs with the updated source
          this.$refs.cropper.replace(event.target.result);
        };
        reader.readAsDataURL(file);
      } else {
        alert("Sorry, FileReader API not supported");
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
