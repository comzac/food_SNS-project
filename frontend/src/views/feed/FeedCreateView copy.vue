<template>
  <div>
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
              autocapitalize="off"
              autocorrect="off"
              autocomplete="off"
            ></v-text-field>
            <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
            <v-window v-model="i2" continuous>
              <v-window-item v-for="(preview, i) in previews" :key="i">
                <v-responsive
                  v-if="preview.includes('data:video/mp4', 0)"
                  class="align-center"
                  aspect-ratio="1"
                  style="background-color:#e0e0e0;"
                >
                  <video
                    :id="i"
                    :src="preview"
                    type="video/mp4"
                    width="100%"
                    class="my-auto"
                    autoplay
                    loop
                    muted
                    playsinline
                  ></video>
                </v-responsive>
                <v-img
                  v-if="!preview.includes('data:video/mp4', 0)"
                  :src="preview"
                  width="100%"
                  :aspect-ratio="1"
                  contain
                  class="grey lighten-2"
                ></v-img>
              </v-window-item>
            </v-window>

            <v-card-actions class="justify-space-between">
              <v-btn text @click="prev" color="#ff6666">
                <v-icon>mdi-chevron-double-left</v-icon>
              </v-btn>
              <v-item-group v-model="i2" class="text-center" mandatory>
                <v-item
                  v-for="n in previews.length"
                  :key="n"
                  v-slot:default="{ active, toggle }"
                >
                  <v-btn
                    :input-value="active"
                    icon
                    x-small
                    @click="toggle"
                    color="#ff6666"
                  >
                    <v-icon>mdi-record</v-icon>
                  </v-btn>
                </v-item>
              </v-item-group>
              <v-btn text @click="next" color="#ff6666">
                <v-icon>mdi-chevron-double-right</v-icon>
              </v-btn>
            </v-card-actions>

            <!-- 사진 비디오 입력 -->
            <v-file-input
              multiple
              v-if="!isUpdatePage"
              v-model="fileData"
              prepend-icon
              accept=".png, .jpeg, .gif, .jpg, .mp4, .jfif"
              outlined
              solo
              label="사진 또는 동영상 선택"
              @change="previewImage"
              color="#ff6666"
              error-messages="20mb 이하 .png, jp(e)g, gif, jfif .mp4 파일만 최대 8개 업로드 됩니다."
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
              autocomplete="off"
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
              error-messages="스페이스바 혹은 엔터, 콤마를 사용하여 태그를 구분할 수 있습니다"
              autocapitalize="off"
              autocorrect="off"
              autocomplete="off"
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
                :disabled="!feed.title || !feed.content || !fileData.length"
                @click="insertFeedByFormData(), (overlay = true)"
                color="#ff6666"
                class="white--text"
                >작성 완료</v-btn
              >
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import swal from "sweetalert";

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
      overlay: false,
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
    next() {
      this.i2 = this.i2 + 1 === this.previews.length ? 0 : this.i2 + 1;
    },
    prev() {
      this.i2 = this.i2 - 1 < 0 ? this.previews.length - 1 : this.i2 - 1;
    },
    previewImage(file) {
      this.previews = [];
      this.fileData = [];
      this.i2 = 0;
      console.log(file);
      if (file.length > 8) {
        // console.log(file.length);
        swal({
          title: "파일 개수 초과",
          text: "파일이 8개보다 많이 선택되었습니다.",
          icon: "warning",
          dangerMode: true,
        });
        return false;
      }
      for (let i = 0; i < file.length; i++) {
        if (file[i].size > 20 * 1024 * 1024) {
          swal({
            title: "파일 용량 초과",
            text: "20mb보다 큰 파일이 선택되었습니다.",
            icon: "warning",
            dangerMode: true,
          });
          return false;
        } else if (
          !/.mp4/.test(file[i].name) &&
          !/.png/.test(file[i].name) &&
          !/.gif/.test(file[i].name) &&
          !/.jfif/.test(file[i].name) &&
          !/.jpg/.test(file[i].name) &&
          !/.jfif/.test(file[i].name) &&
          !/.jpeg/.test(file[i].name)
        ) {
          swal({
            title: "파일 형식 문제",
            text: "파일 형식이 어긋납니다.",
            icon: "error",
            dangerMode: true,
          });
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
      // form.append("id", this.$route.params.fid);
      this.feedData.feed = this.feed;
      let hashtagData = [];
      this.feedhashtag.forEach((hashtag) => {
        hashtagData.push({
          content: hashtag,
        });
      });
      this.feedData.hashtag = hashtagData;
      this.feedData.id = this.$route.params.fid;
      this.updateFeed(this.feedData);
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
      // this.feedhashtag = this.selectedFeed.hashtag;
      this.selectedFeed.hashtag.forEach((tag) => {
        console.log("aa");
        console.log(tag);
        this.feedhashtag.push(tag.content);
      });
    },
  },

  created() {
    if (this.$route.params.fid) {
      this.getFeedDetail(this.$route.params.fid).then(this.initData());
    }
  },
  mounted() {
    this.$emit("change-page", 7);
    window.scrollTo(0, 0);
  },
};
</script>

<style></style>
