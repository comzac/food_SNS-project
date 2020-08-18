import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";

import LoginView from "@/views/LoginView";
import SignupView from "@/views/SignupView";
import PasswordChoiceView from "@/views/user/PasswordChoiceView";
import PasswordChoiceEmailView from "@/views/user/PasswordChoiceEmailView";
import RetireveIDEmailView from "@/views/user/RetrieveIDEmailView";
import UserDetailView from "@/views/user/UserDetailView";
import UserFollowView from "@/views/user/UserFollowView";
import UserEditView from "@/views/user/UserEditView";
import UserProfileEditView from "@/views/user/UserProfileEditView";
import UserAdditionalDataEditView from "@/views/user/UserAdditionalDataEditView";
import SocialLoginDataInputView from "@/views/user/SocialLoginDataInputView";

import Home from "@/views/Home";

import SearchingView from "@/views/SearchingView";
import SearchedView from "@/views/SearchedView";

// import FeedItem2 from "../views/FeedItem2";
import FeedView from "@/views/feed/FeedView";
import FeedCreateView from "@/views/feed/FeedCreateView";
import CommentView from "@/views/feed/CommentView";

import Liquor from "@/views/LiquorView";
import NotificationView from "@/views/notification/NotificationView";

import ContestList from "@/views/contest/ContestListView";
import ContestFeed from "@/views/contest/ContestFeedView";
import ContestFeedCreate from "@/views/contest/ContestFeedCreateView";

import NotFoundComponent from "@/components/NotFoundComponent";
import ErrorComponent from "@/components/ErrorComponent";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "Home", component: Home, meta: { title: "HoneyCombo" } },
  { path: "/error", name: "Error", component: ErrorComponent },
  {
    path: "/login",
    name: "Login",
    component: LoginView,
    meta: { title: "Login · HoneyCombo" },
  },
  {
    path: "/signup",
    name: "Signup",
    component: SignupView,
    meta: { title: "Signup · HoneyCombo" },
  },
  { path: "*", name: "NotFound", component: NotFoundComponent },
  {
    path: "/user/password_choice",
    name: "PasswordChoice",
    component: PasswordChoiceView,
    meta: { title: "비밀번호 찾기 · HoneyCombo" },
  },
  {
    path: "/user/password_choice_email",
    name: "PasswordChoiceEmail",
    component: PasswordChoiceEmailView,
    meta: { title: "이메일로 비밀번호 찾기 · HoneyCombo" },
  },
  {
    path: "/user/retrieveid",
    name: "RetrieveID",
    component: RetireveIDEmailView,
    meta: { title: "아이디 찾기 · HoneyCombo" },
  },
  {
    path: "/userdetail/:uid",
    name: "UserDetail",
    component: UserDetailView,
  },
  {
    path: "/followings/:uid",
    name: "Following",
    component: UserFollowView,
  },
  {
    path: "/followers/:uid",
    name: "Follower",
    component: UserFollowView,
  },
  {
    path: "/user/edit",
    name: "UserEdit",
    component: UserEditView,
    meta: { title: "회원정보 수정 · HoneyCombo" },
  },
  {
    path: "/userprofile/edit/:uid",
    name: "UserProfileEdit",
    component: UserProfileEditView,
    meta: { title: "프로필 수정 · HoneyCombo" },
  },
  {
    path: "/user/edit/additional",
    name: "UserAdditionalDataEdit",
    component: UserAdditionalDataEditView,
    meta: { title: "추가정보 입력 · HoneyCombo" },
  },
  {
    path: "/user/additionalinfo",
    name: "SocialLoginDataInput",
    component: SocialLoginDataInputView,
    meta: { title: "추가정보 입력 · HoneyCombo" },
  },

  // feed
  {
    path: "/feed/:fid",
    name: "FeedView",
    component: FeedView,
  },
  {
    path: "/feed/create/:fid?",
    name: "FeedCreateView",
    component: FeedCreateView,
  },
  {
    path: "/feed/comment/:fid",
    name: "CommentView",
    component: CommentView,
  },

  {
    path: "/search",
    name: "SearchingView",
    component: SearchingView,
  },
  {
    path: "/search/:keyword",
    name: "SearchedView",
    component: SearchedView,
  },
  {
    path: "/notifications",
    name: "NotificationView",
    component: NotificationView,
  },

  // {
  //   path: "/feed/:fid",
  //   name: "FeedItem2",
  //   component: FeedItem2,
  // },
  {
    path: "/liquor",
    name: "Liquor",
    component: Liquor,
  },
  {
    path: "/contest",
    name: "ContestList",
    component: ContestList,
    meta: { title: "콘테스트 · HoneyCombo" },
  },
  {
    path: "/contest/:fid",
    name: "ContestFeed",
    component: ContestFeed,
  },
  {
    path: "/contest/feed/create/:fid?",
    name: "ContestFeedCreate",
    component: ContestFeedCreate,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  // console.log("to : ", to);
  // console.log("from : ", from);
  to;
  from;

  if (to.meta.title) {
    document.title = to.meta.title;
  }

  if (to.name === "UserEdit" && from.name === "UserProfileEdit") {
    store.dispatch("accounts/setAuthorized", true);
  }

  const publicPages = [
    "Signup",
    "Login",
    "NotFound",
    "PasswordChoice",
    "PasswordChoiceEmail",
    "RetrieveID",
  ];
  const needNotAuthPages = ["Signup", "Login"];

  const authRequired = !publicPages.includes(to.name);
  const unauthRequired = needNotAuthPages.includes(to.name);
  const isLoggedIn = !!window.$cookies.isKey("auth-token");

  if (unauthRequired && isLoggedIn) {
    next("/");
  }
  authRequired && !isLoggedIn ? next({ name: "Login" }) : next(); // 우선 로그인 안 된 유저는 모든 페이지에 대해 login페이지로 가게끔 함.
});

export default router;
