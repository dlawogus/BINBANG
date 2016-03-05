<?php
/*
    게시판 페이징 모듈
    작성자 : 송 웅
 
    넘겨 받을 데이터
    $total_col          // 총 게시글 수
    $now_page           // 현재 페이지 번호
    $page_col_num       // 한 페이지 게시글 수
    $page_block_num     // 한 페이지 블럭 수
 */
  function page_nav($total,$scale,$p_num,$page,$query){
      global $PHP_SELF;

      $total_page = ceil($total/$scale);
      if (!$page) $page = 1;
      $page_list = ceil($page/$p_num)-1;

      // 페이지 리스트의 첫번째가 아닌 경우엔 [1]...[prev] 버튼을 생성한다.
      if ($page_list>0) 
      {
             $navigation = "<a href=\"$PHP_SELF?page=1&$query\">[1]</a> ... "; 
             $prev_page = ($page_list)*$p_num; 
             $navigation .= "<a href=\"$PHP_SELF?page=$prev_page&$query\">[prev]</a>"; 
      }

      // 페이지 목록 가운데 부분 출력
      $page_end=($page_list+1)*$p_num;
      if ($page_end>$total_page) $page_end=$total_page;

      for ($setpage=$page_list*$p_num+1;$setpage<=$page_end;$setpage++)
      {
             if ($setpage==$page) {
                     $navigation .= "<strong>[$setpage]</strong> ";
             } else {
                     $navigation .= "<a href='$PHP_SELF?page=$setpage&$query' class=num>[$setpage]</a> ";
             }
      }

      // 페이지 목록 맨 끝이 $total_page 보다 작을 경우에만, [next]...[$total_page] 버튼을 생성한다.
      if ($page_end<$total_page) 
      {
             $next_page = ($page_list+1)*$p_num+1;
             $navigation .= "<a href='$PHP_SELF?page=$next_page&$query'>[next]</a> ";
             $navigation .= "... <a href='$PHP_SELF?page=$total_page&$query'>[$total_page]</a>";
      }
      return $navigation;
  }
?>