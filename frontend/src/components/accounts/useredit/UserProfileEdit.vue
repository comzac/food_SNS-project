<template>
  <v-col cols="12" sm="8" md="6" lg="4">
    <h2 class="text-left red--text text--lighten-2 ml-3">프로필 수정</h2>
    <v-container>
      <v-row>
        <v-col cols="4">
          <v-avatar
            size="70"
            :color="data.hasImage || imageData ? 'white' : 'grey'"
          >
            <v-icon large v-show="!data.hasImage && !imageData" dark
              >mdi-account</v-icon
            >
            <v-img
              v-if="data.hasImage && !imageData"
              :src="`data:${authUserImgType};base64,${authUserImgData}`"
              :alt="authUserImgName"
            />
            <v-img v-if="imageData" :src="imageData" />
          </v-avatar>
        </v-col>
        <v-col
          v-if="!inputPhase"
          cols="4"
          class="d-flex justify-center align-center"
        >
          <v-btn
            class="ml-4"
            fab
            dark
            color="#ff6666"
            @click="inputPhase = !inputPhase"
          >
            <v-icon large dark>mdi-image</v-icon>
          </v-btn>
        </v-col>
        <v-col
          v-if="!inputPhase"
          cols="4"
          class="d-flex justify-center align-center"
        >
          <v-btn fab dark color="#ff6666" @click="removeProfileImg">
            <v-icon large dark>mdi-cached</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="6">
          <!-- 사진 입력 -->
          <v-file-input
            v-model="data.img"
            v-show="inputPhase"
            truncate-length="15"
            class="mt-3"
            prepend-icon
            accept=".png, .jpeg, .gif, .jpg"
            outlined
            solo
            label="사진 선택"
            @change="previewImage"
            color="#ff6666"
            error-messages="png, jpeg, gif, jpg 형식"
          ></v-file-input>
        </v-col>
        <v-col cols="2">
          <v-btn
            v-show="inputPhase"
            fab
            small
            class="ml-n1 mt-5"
            @click="resetSelectImg"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>

    <!-- 프로필 문구 -->
    <v-textarea
      color="#ff6666"
      class="mt-n3"
      rows="1"
      counter
      v-model="data.text"
      :rules="rules"
      label="내용"
      outlined
      solo
      single-line
      auto-grow
      autocomplete="off"
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
      :error-messages="nickErrorMsg"
      @input="nickcheck = false"
      autocomplete="off"
    ></v-text-field>
    <v-btn
      color="grey"
      class="white--text mx-3 mt-7"
      width="40%"
      @click="$router.go(-1)"
      >취소</v-btn
    >
    <v-btn
      color="#ff6666"
      width="40%"
      class="white--text mx-3 mt-7"
      @click="proceed"
      :disabled="!dataChanged"
      >진행</v-btn
    >
  </v-col>
</template>

<script>
import swal from "sweetalert";

import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      rules: [
        (v) => v.length <= 85 || "프로필 문구는 85자까지 작성할 수 있습니다.",
      ],
      nickcheck: true,
      imageData: "",
      data: {
        unick: "",
        text: "",
        img: null,
        hasImage: false,
      },
      inputPhase: false,
    };
  },
  computed: {
    ...mapGetters("accounts", [
      "authUserImgData",
      "authUserImgType",
      "authUserImgName",
      "authUserProfileText",
      "authUserUnick",
    ]),
    nickErrorMsg() {
      if (this.authUserUnick === this.data.unick || this.nickcheck) {
        return "";
      } else return "오른쪽의 체크를 눌러 중복확인해주세요";
    },
    dataChanged() {
      if (
        !this.data.img &&
        this.data.text === this.authUserProfileText &&
        this.data.unick === this.authUserUnick
      ) {
        if (!this.data.hasImage && this.authUserImgData) {
          return true;
        }
        return false;
      } else return true;
    },
  },
  methods: {
    ...mapActions("accounts", ["nickCheck"]),
    ...mapActions("feeds", ["setUserProfileData"]),
    nickCheck2(unick) {
      // if분기로 받아온 unick과 입력한 값이 다를 때만 nickCheck 하도록
      if (unick !== this.authUserUnick) {
        this.nickCheck(unick).then((res) =>
          res ? (this.nickcheck = true) : (this.nickcheck = false)
        );
      } else this.nickcheck = true;
    },
    previewImage(file) {
      if (file) {
        if (file.size > 200 * 1024) {
          swal({
            text: "프로필 사진은 200kb 를 넘을 수 없습니다.",
            dangerMode: true,
          });
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
    setInitData() {
      (this.data.unick = this.authUserUnick),
        (this.data.text = this.authUserProfileText);
      if (this.authUserImgData) {
        this.data.hasImage = true;
      }
    },
    proceed() {
      if (this.nickcheck) {
        if (this.data.img) {
          this.data.hasImage = true;
        }
        if (!this.dataChanged) {
          swal("변경사항이 없습니다.");
          this.$router.go(-1);
        } else {
          this.setUserProfileData(this.data);
        }
      } else swal({ text: "닉네임 중복체크를 해주세요.", dangerMode: true });
    },
    resetSelectImg() {
      this.inputPhase = !this.inputPhase;
      this.data.img = null;
      this.imageData = "";
    },
    removeProfileImg() {
      if (this.data.hasImage) {
        swal({
          text: "프로필 사진을 삭제하시겠습니까?",
          icon: "warning",
          buttons: true,
          dangerMode: true,
        }).then((willDelete) => {
          if (willDelete) {
            this.data.hasImage = false;
            swal("프로필 사진이 삭제되었습니다.", {
              icon: "success",
            });
          }
        });
        // const remove = confirm("프로필 사진을 삭제하시겠습니까?");
        // if (remove) {
        //   this.data.hasImage = false;
        // }
      }
    },
  },
  created() {
    this.setInitData();
  },
};
</script>

<style></style>
