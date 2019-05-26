package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {

    private MatchController matchController;

    @BeforeEach
    void setUp() {
        try {
            matchController = new MatchController();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    void getMap() {
    }

}