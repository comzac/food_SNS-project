<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-text-field
          label="제목"
          outlined
          solo
          name="title"
          type="text"
          v-model="feed.title"
          color="#ff6666"
          :error-messages="feed.title?'':'제목을 입력해주세요'"
          autocapitalize="off"
          autocorrect="off"
        ></v-text-field>
        <video width="100%" controls :src="video" type="video/mp4" autoplay></video>
        <v-file-input
          prepend-icon
          accept=".mp4"
          outlined
          solo
          label="비디오 선택"
          @change="previewVideo"
          color="#ff6666"
          :error-messages="video?'':'눌러서 비디오을 선택하세요 (.mp4 파일만 업로드 됩니다.)'"
        ></v-file-input>
        <v-img :src="imageData" lazy-src="@/assets/img-placeholder.png" aspect-ratio="1"></v-img>
        <!-- 사진 입력 -->
        <v-file-input
          prepend-icon
          accept=".png, .jpeg, .gif, .jpg"
          outlined
          solo
          label="사진 선택"
          @change="previewImage"
          color="#ff6666"
          :error-messages="imageData?'':'눌러서 사진을 선택하세요 (.png, jpeg, gif, jpg 파일만 업로드 됩니다.)'"
        ></v-file-input>
        <v-spacer></v-spacer>
        <v-textarea
          color="#ff6666"
          v-model="feed.content"
          auto-grow
          label="내용"
          outlined
          solo
          :error-messages="feed.content?'':'내용을 입력하세요'"
          single-line
        ></v-textarea>
        <v-spacer>
          <br />
        </v-spacer>
        <!-- 수정 필요 -->
        <v-text-field
          label="태그"
          outlined
          solo
          name="태그"
          type="text"
          v-model="feedhashtag"
          color="#ff6666"
          append-icon="mdi-plus"
          @click:append="createHashtag(hashtag)"
          @keyup.enter.space="createHashtag(hashtag)"
          error-messages="스페이스바 혹은 엔터를 사용하여 태그를 구분할 수 있습니다"
          autocapitalize="off"
          autocorrect="off"
        ></v-text-field>
        <div v-for="tag in feedhashtag" :key="tag" style="display: inline-block;">
          <v-btn
            outlined
            class="red--text text--lighten-2"
            color="#ff6666"
            small
            @click="tag.splice(feedhashtag.indexOf(tag), 1)"
          ># {{ tag }}</v-btn>
        </div>
        <v-spacer>
          <br />
        </v-spacer>
        <div>
          <v-btn @click="$router.go(-1)" class="white--text" color="#666666" width="99">취소</v-btn>
          <v-divider class="mr-5" vertical></v-divider>
          <!-- 클릭하면 피드 상세 페이지로 -->
          <v-btn
            :disabled="!feed.title || !feed.content || !fileData"
            @click="insertFeedByFormData()"
            color="#ff6666"
            class="white--text"
          >작성 완료</v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "FeedCreateView",
  components: {},
  data() {
    return {
      hashtag: "",
      imageData: "",
      fileData: [],
      feed: {
        title: "",
        content: "",
      },
      feedhashtag: [],
      video: "",
    };
  },
  methods: {
    ...mapActions("feeds", ["insertFeed"]),
    // previewImage(event) {
    //   // Reference to the DOM input element
    //   console.log(event.target);
    //   var input = event.target;
    //   // Ensure that you have a file before attempting to read it
    //   if (input.files && input.files[0]) {
    //     // create a new FileReader to read this image and convert to base64 format
    //     var reader = new FileReader();
    //     // Define a callback function to run, when FileReader finishes its job
    //     reader.onload = (event) => {
    //       // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
    //       // Read image as base64 and set to imageData
    //       this.imageData = event.target.result;
    //       console.log(event.target);
    //     };
    //     // Start the reader job - read file as a data url (base64 format)
    //     reader.readAsDataURL(input.files[0]);
    //   }
    // },
    previewImage(file) {
      console.log(file);
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
          this.fileData.push(file.target.result);
        };
        // Start the reader job - read file as a data url (base64 format)
        reader.readAsDataURL(file);
      }
    },
    previewVideo(file) {
      console.log(file);
      if (file.size > 20 * 1024 * 1024) {
        alert("파일 사이즈가 20mb 보다 큽니다.");
        return false;
      } else {
        var reader = new FileReader();
        reader.onload = (file) => {
          this.fileData.push(file.target.result);
          this.video = file.target.result;
        };
        reader.readAsDataURL(file);
      }
    },

    insertFeedByFormData() {
      const form = new FormData();

      form.append("feed", this.feed);
      this.feedhashtag.forEach((tag) => {
        if (tag !== "") {
          form.append("hashtag", tag);
        }
      });
      this.fileData.forEach((file) => {
        form.append("file", file);
      });
      this.insertFeed(form);
    },

    createHashtag(hashtag) {
      hashtag = hashtag.replace(/#/gi, "").replace(/ /gi, "");
      console.log(this.feedhashtag);
      if (this.feedhashtag.includes(hashtag) || hashtag == "") {
        this.hashtag = "";
      } else {
        this.feedhashtag.push(hashtag);
        this.hashtag = "";
      }
      console.log(this.feedhashtag);
    },
  },
};
</script>

<style>
</style>