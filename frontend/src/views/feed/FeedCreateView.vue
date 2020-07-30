<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat class="mx-auto" max-width="614">
          <v-text-field
            label="제목"
            outlined
            solo
            name="title"
            type="text"
            v-model="feed.title"
            color="#ff6666"
            :error-messages="feed.title ? '' : '제목을 입력해주세요'"
            autofocus
            autocapitalize="off"
            autocorrect="off"
          ></v-text-field>
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
                width="100%"
                class="my-auto"
              ></video>
            </v-responsive>
            <v-img
              v-if="!preview.includes('data:video/mp4', 0)"
              v-show="i == i2"
              :id="i"
              :src="preview"
              width="100%"
              aspect-ratio="1"
            ></v-img>
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

          <!-- 사진 비디오 입력 -->
          <v-file-input
            multiple
            v-if="!isUpdatePage"
            v-model="fileData"
            prepend-icon
            accept=".png, .jpeg, .gif, .jpg, .mp4"
            outlined
            solo
            label="사진 또는 동영상 선택"
            @change="previewImage"
            color="#ff6666"
            error-messages=".png, jpeg, gif, jpg .mp4 파일만 최대 3개 업로드 됩니다."
          ></v-file-input>
          <v-spacer></v-spacer>
          <v-textarea
            color="#ff6666"
            v-model="feed.content"
            auto-grow
            label="내용"
            outlined
            solo
            :error-messages="feed.content ? '' : '내용을 입력하세요'"
            single-line
          ></v-textarea>
          <v-spacer>
            <br />
          </v-spacer>
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
            v-model="hashtag"
            append-icon="mdi-plus"
            @click:append="createHashtag(hashtag)"
            @keyup.enter.space.,="createHashtag(hashtag)"
            error-messages="스페이스바 혹은 엔터를 사용하여 태그를 구분할 수 있습니다"
            autocapitalize="off"
            autocorrect="off"
          ></v-text-field>
          <div
            v-for="tag in feedhashtag"
            :key="tag"
            style="display: inline-block;"
          >
            <v-btn
              outlined
              solo
              name="title"
              type="text"
              v-model="feed.title"
              color="#ff6666"
              small
              @click="feedhashtag.splice(feedhashtag.indexOf(tag), 1)"
              ># {{ tag }}</v-btn
            >
          </div>
          <v-spacer>
            <br />
          </v-spacer>
          <div>
            <v-btn
              @click="$router.go(-1)"
              class="white--text"
              color="#666666"
              width="99"
              >취소</v-btn
            >
            <v-divider class="mr-5" vertical></v-divider>
            <!-- 클릭하면 피드 상세 페이지로 -->
            <v-btn
              v-if="isUpdatePage"
              :disabled="!feed.title || !feed.content || !fileData"
              @click="updateFeedByFormData()"
              color="#ff6666"
              class="white--text"
              >작성 완료</v-btn
            >
            <v-btn
              v-else
              :disabled="!feed.title || !feed.content || !fileData"
              @click="insertFeedByFormData()"
              color="#ff6666"
              class="white--text"
              >작성 완료</v-btn
            >
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "FeedCreateView",
  components: {},
  computed: {
    ...mapState("feeds", ["selectedFeed"]),
    isUpdatePage() {
      return !!this.$route.params.fid;
    },
  },
  data() {
    return {
      i2: 0,
      feedData: {},
      previews: [],
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
    ...mapActions("feeds", ["insertFeed", "updateFeed", "getFeedDetail"]),

    previewImage(file) {
      this.previews = [];
      this.fileData = [];
      this.i2 = 0;
      console.log(file);
      if (file.length > 3) {
        // console.log(file.length);
        alert("파일이 3개보다 많이 선택되었습니다.");
        return false;
      }
      for (let i = 0; i < file.length; i++) {
        if (file[i].size > 20 * 1024 * 1024) {
          alert("파일 사이즈가 20mb 보다 큽니다.");
          return false;
        } else {
          // console.log(file[i]);
          this.fileData.push(file[i]);
        }
      }
      for (let i = 0; i < this.fileData.length; i++) {
        let reader = new FileReader();
        reader.onload = () => {
          this.fileData[i].src = reader.result;
          this.previews.push(reader.result);
        };
        reader.readAsDataURL(this.fileData[i]);
      }
      console.log(this.previews);
      console.log(this.fileData);
      setTimeout(function() {
        document.getElementById("slider").click();
      }, 500);
      // if (file.size > 20 * 1024 * 1024) {
      //   alert("파일 사이즈가 20mb 보다 큽니다.");
      //   return false;
      // } else {
      //   // Reference to the DOM input element
      //   var reader = new FileReader();
      //   // Define a callback function to run, when FileReader finishes its job
      //   reader.onload = (file) => {
      //     // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
      //     // Read image as base64 and set to imageData
      //     this.imageData = file.target.result;
      //     this.fileData.push(file.target.result);
      //   };
      //   // Start the reader job - read file as a data url (base64 format)
      //   reader.readAsDataURL(file);
      // }
    },
    insertFeedByFormData() {
      // const form = new FormData();

      // form.append("feed", this.feed);
      // this.feedhashtag.forEach((tag) => {
      //   if (tag !== "") {
      //     form.append("hashtag", tag);
      //   }
      // });
      // this.fileData.forEach((file) => {
      //   form.append("file", file);
      // });
      this.feedData.feed = this.feed;
      let hashtagData = [];
      this.feedhashtag.forEach((hashtag) => {
        hashtagData.push({
          content: hashtag,
        });
      });
      this.feedData.hashtag = hashtagData;
      this.feedData.dbFiles = this.fileData;
      this.insertFeed(this.feedData);
    },

    updateFeedByFormData() {
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
      form.append("id", this.$route.params.fid);
      this.updateFeed(form);
    },

    createHashtag(hashtag) {
      hashtag = hashtag
        .replace(/#/gi, "")
        .replace(/ /gi, "")
        .replace(/,/gi, "")
        .toUpperCase();
      console.log(hashtag);
      if (this.feedhashtag.includes(hashtag) || hashtag == "") {
        this.hashtag = "";
      } else {
        this.feedhashtag.push(hashtag);
        this.hashtag = "";
      }
      console.log(this.feedhashtag);
    },

    initData() {
      this.feed.title = this.selectedFeed.feed.title;
      this.feed.content = this.selectedFeed.feed.content;
      // this.fileData = this.selectedFeed.dbFiles;
      this.feedhashtag = this.selectedFeed.hashtag;
    },
  },

  created() {
    if (this.$route.params.fid) {
      this.getFeedDetail(this.$route.params.fid).then(this.initData());
    }
  },
};
</script>

<style></style>
