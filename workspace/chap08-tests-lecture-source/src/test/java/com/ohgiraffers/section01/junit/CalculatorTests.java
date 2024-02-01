package com.ohgiraffers.section01.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* 단위(유닛) 테스트
 * 한 가지 기능(함수)마다 일을 잘 수행하는지 확인하며 특정 모듈이 의도 된 대로 정확히 작동하는지 검증하는 절차
 * 연관 컴포넌트가 개발 되지 않더라도 기능별 개발이 완료 된 것을 증명할 수 있음
 * */
public class CalculatorTests {

    private Calculator calc = null;

    /* 테스트 시나리오 */
    /* 1. Calculator 인스턴스 생성이 잘 되는지 테스트
     * @BeforeEach : @Test 가 붙은 테스트 메소드 하나를 실행하기 전마다 실행되는 메소드 */
    @BeforeEach
    public void setup() {
        System.out.println("calculator 인스턴스 생성");
        calc = new Calculator();
    }

    /* 2. sumTwoNumber 메소드가 정상 기능 하는지 테스트 */
    @Test
    public void sumTwoNumber_4와_5를_전달하면_합계가_9가_되는지_확인() {
        int result = calc.sumTwoNumber(4, 5);

        /* assertEquals(예상값, 변수명) */
        assertEquals(9, result);
        /* 아래는 값이 달라 테스트 실패 메세지가 뜸 */
//        assertEquals(10, result);
    }

    @Test
    public void sumTwoNumber_6과_7을_전달하면_합계가_13이_되는지_확인() {
        int result = calc.sumTwoNumber(6, 7);

        /* assertEquals(예상값, 변수명, 오차범위) */
        assertEquals(12, result, 1);
    }

    /* @AfterAll : 모튼 테스트 수행이 끝나고 호출 되는 메소드 */
    @AfterAll
    public static void afterTest() {
        System.out.println("테스트 완료!");
    }


}
