<template>
  <v-col cols="12" sm="8" md="6" lg="4">
    <h2 class="text-left red--text text--lighten-2 ml-3">프로필 수정</h2>
    <v-img :src="imageData" lazy-src="@/assets/img-placeholder.png" aspect-ratio="1"></v-img>
    <!-- 사진 입력 -->
    <v-file-input
      v-model="data.img"
      prepend-icon
      accept=".png, .jpeg, .gif, .jpg"
      outlined
      solo
      label="사진 선택"
      @change="previewImage"
      color="#ff6666"
      :error-messages="imageData?'':'눌러서 사진을 선택하세요 (.png, jpeg, gif, jpg 파일만 업로드 됩니다.)'"
    ></v-file-input>

    <!-- 프로필 문구 -->
    <v-textarea
      color="#ff6666"
      counter
      v-model="data.text"
      :rules="rules"
      label="내용"
      outlined
      solo
      single-line
    ></v-textarea>
    <v-text-field
      label="별명"
      name="nickname"
      prepend-icon="mdi-account-box"
      type="text"
      v-model="data.unick"
      color="#ff6666"
      append-outer-icon="mdi-check"
      @click:append-outer="nickCheck2(data.unick)"
      :error-messages="nickcheck?'':'오른쪽의 체크를 눌러 중복확인해주세요'"
      @input="nickcheck = false"
    ></v-text-field>
    <v-btn color="grey" class="white--text mx-3" width="40%" @click="$router.go(-1)">취소</v-btn>
    <v-btn color="#ff6666" width="40%" class="white--text mx-3" @click="setUserDetailData(data)">진행</v-btn>
  </v-col>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      rules: [
        (v) => v.length <= 85 || "프로필 문구는 85자까지 작성할 수 있습니다.",
      ],
      nickcheck: false,
      imageData: "",
      data: {
        unick: "",
        text: "",
        img: null,
      },
    };
  },
  methods: {
    ...mapActions("accounts", ["nickCheck"]),
    ...mapActions("feeds", ["setUserDetailData"]),
    nickCheck2(unick) {
      // if분기로 받아온 unick과 입력한 값이 다를 때만 nickCheck 하도록
      this.nickCheck(unick).then((res) =>
        res ? (this.nickcheck = true) : (this.nickcheck = false)
      );
    },
    previewImage(file) {
      if (file) {
        if (file.size > 20 * 1024 * 1024) {
          alert("파일 사이즈가 20mb 보다 큽니다.");
          return false;
        } else {
          // Reference to the DOM input element
          var reader = new FileReader();
          // Define a callback function to run, when FileReader finishes its job
          reader.onload = (file) => {
            // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
            // Read image as base64 and set to imageData
            this.imageData = file.target.result;
          };
          // Start the reader job - read file as a data url (base64 format)
          reader.readAsDataURL(file);
        }
      }
    },
  },
};
</script>

<style>
</style>