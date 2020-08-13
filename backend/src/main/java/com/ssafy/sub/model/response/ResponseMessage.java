package com.ssafy.sub.model.response;

public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String SOCIAL_LOGIN_SUCCESS = "소셜로그인 성공";
    public static final String SOCIAL_LOGIN_FAIL = " 토큰 정보가 유효하지 않습니다.";
    public static final String LOGIN_FAIL_ID = "아이디를 확인해주세요";
    public static final String LOGIN_FAIL_PW = "비밀번호를 확인해주세요";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String LOGOUT_FAIL = "로그아웃 실패";
    public static final String NOT_FOUND_BIRTH = "생일을 입력해주세요";
    
    public static final String READ_NOTIFICATION = "알림 조회 성공";
    public static final String FAIL_READ_NOTIFICATION = "알림 조회 실패";
    public static final String READ_ALL_NOTIFICATION = "모든 알림 조회 성공";
    
    public static final String READ_ALL_USERS = "모든 유저 조회 성공";
    public static final String READ_SEARCHED_USERS = "검색 유저 조회 성공";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String ALREADY_USER = "이미 존재하는 Email입니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String FAIL_CREATE_USER = "회원 가입 실패";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String FAIL_UPDATE_USER = "회원 정보 수정 실패";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    
    public static final String READ_ALL_FEEDS = "모든 피드 조회 성공";
    public static final String READ_USER_FEEDS = "해당 유저 피드 조회 성공";
    public static final String READ_FEED = "피드 조회 성공";
    public static final String NOT_FOUND_FEED = "피드가 존재하지 않습니다.";
    public static final String CREATE_FEED = "피드 작성 성공";
    public static final String FAIL_CREATE_FEED = "피드 작성 실패";
    public static final String UPDATE_FEED = "피드 업데이트 성공";
    public static final String FAIL_UPDATE_FEED = "피드 업데이트 실패";
    public static final String DELETE_FEED = "피드 삭제 성공";
    public static final String FAIL_DELETE_FEED = "피드 업데이트 실패";
    public static final String LIKE_FEED = "피드 좋아요";
    public static final String UNLIKE_FEED = "피드 좋아요 해제";
    public static final String FAIL_LIKE_FEED = "피드 좋아요 실패";
    public static final String FAIL_UNLIKE_FEED = "피드 좋아요 해제 실패";
    public static final String NOT_FOUND_FEED_LIKE = "피드 좋아요를 하지 않았습니다.";
    
    public static final String NOT_FOUND_HASHTAG = "해시태그 조회 실패";
    public static final String CREATE_HASHTAG = "해시태그 생성 성공";
    public static final String FAIL_CREATE_HASHTAG = "해시태그 생성 실패";
    public static final String UPDATE_HASHTAG = "해시태그 수정 성공";
    public static final String FAIL_UPDATE_HASHTAG = "해시태그 수정 실패";
    public static final String READ_SEARCHED_HASHTAG = "검색 해시태그 조회 성공";

    public static final String NOT_FOUND_FOLLOW = "팔로우 정보 조회 실패";
    public static final String CREATE_FOLLOWER = "팔로워 생성 성공";
    public static final String FAIL_CREATE_FOLLOWER = "팔로워 생성 실패";
    public static final String FAIL_CREATE_FOLLOWER_SAME = "팔로워 생성 실패 - 본인 팔로우 불가능";
    public static final String FAIL_CREATE_FOLLOWER_DUP = "팔로워 생성 실패 - 이미 팔로우 된 상태";
    public static final String READ_FOLLOWER = "팔로워 조회 성공";
    public static final String READ_FOLLOWING = "팔로잉 조회 성공";
    public static final String DELETE_FOLLOWER = "팔로워 삭제 성공";
    public static final String FAIL_DELETE_FOLLOWER = "팔로워 삭제 실패";
    
    public static final String READ_ALL_COMMENTS = "모든 댓글 조회 성공";
    public static final String READ_COMMENT = "댓글 조회 성공";
    public static final String NOT_FOUND_COMMENT = "댓글이 존재하지 않습니다.";
    public static final String CREATE_COMMENT = "댓글 작성 성공";
    public static final String FAIL_CREATE_COMMENT = "댓글 작성 실패";
    public static final String UPDATE_COMMENT = "댓글 수정 성공";
    public static final String FAIL_UPDATE_COMMENT = "댓글 수정 실패";
    public static final String DELETE_COMMENT = "댓글 삭제 성공";
    public static final String FAIL_DELETE_COMMENT = "댓글 삭제 실패";
    public static final String LIKE_COMMENT = "댓글 좋아요";
    public static final String UNLIKE_COMMENT = "댓글 좋아요 해제";
    public static final String FAIL_LIKE_COMMENT = "댓글 좋아요 실패";
    public static final String FAIL_UNLIKE_COMMENT = "댓글 좋아요 해제 실패";
    
    public static final String READ_ALL_CONTEST = "모든 콘테스트 조회 성공";
    public static final String NOT_FOUND_CONTEST = "콘테스트가 없습니다";

    public static final String AUTHORIZED = "인증 성공";
    public static final String UNAUTHORIZED = "인증 실패";
    public static final String FORBIDDEN = "인가 실패";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String SERVICE_UNAVAILABLE = "현재 서비스를 사용하실 수 없습니다. 잠시후 다시 시도해 주세요.";

    public static final String DB_ERROR = "데이터베이스 에러";

    public static final String CREATE_FILE = "파일 저장 성공";
    
    public static final String LOG_UPDATE_SUCCESS = "유저 로그 데이터 분석 성공";
}
