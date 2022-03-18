package mcm.edu.ph.lastname_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coching_turnbasedgame.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    TextView txtHeroName,txtMonsName,txtHeroHP,txtMonsHP,txtHeroMP,txtMonsMP,txtHeroDPS,txtMonsDPS,txtLog;
    Button btnNextTurn;
    ImageButton skill1,skill2,skill3,skill4;

    String heroName = "Mhike Santos";
    int heroHp = 2000;
    int heroMP = 1000;
    int heroMinDmg = 200;
    int heroMaxDmg = 350;

    String badName = "Giga Orc";
    int badHp = 2500;
    int badMp = 500;
    int badMinDmg = 100;
    int badMaxDmg = 150;

    int turnNumber= 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHeroName = findViewById(R.id.txtHeroName);
        txtMonsName = findViewById(R.id.txtMonsterName);
        txtHeroHP = findViewById(R.id.txtHeroHP);
        txtMonsHP = findViewById(R.id.txtMonsterHP);
        txtHeroMP = findViewById(R.id.txtHeroMP);
        txtMonsMP = findViewById(R.id.txtMonsterMP);
        btnNextTurn = findViewById(R.id.btnNxtTurn);
        txtHeroDPS = findViewById(R.id.txtHeroDPS);
        txtMonsDPS = findViewById(R.id.txtMonsterDPS);

        txtLog = findViewById(R.id.txtCombatLog);

        txtHeroName.setText(heroName);
        txtHeroHP.setText(String.valueOf(heroHp));
        txtHeroMP.setText(String.valueOf(heroMP));

        txtMonsName.setText(badName);
        txtMonsHP.setText(String.valueOf(badHp));
        txtMonsMP.setText(String.valueOf(badMp));

        txtHeroDPS.setText(String.valueOf(heroMinDmg)+ " ~ "+ String.valueOf(heroMaxDmg));
        txtMonsDPS.setText(String.valueOf(badMinDmg)+ " ~ "+ String.valueOf(badMaxDmg));

        skill1 = findViewById(R.id.btnSkill1);

        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        txtLog.findViewById(R.id.txtCombatLog);

        Random randomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDmg - heroMinDmg) + heroMinDmg;
        int monsdps = randomizer.nextInt(badMaxDmg - badMinDmg) + badMinDmg;

        if(turnNumber % 2 != 1){
            skill1.setEnabled(false);
        }
        else if(turnNumber%2 == 1){
            skill1.setEnabled(true);
        }

        if(buttoncounter>0){
            skill1.setEnabled(false);
        }

        else if(buttoncounter==0){
            skill1.setEnabled(true);
        }

        switch(v.getId()) {
            case R.id.btnSkill1:

                badHp = badHp - 250;
                turnNumber++;
                txtMonsHP.setText(String.valueOf(badHp));
                btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                txtLog.setText("Hero "+String.valueOf(heroName) +" used stun!. It dealt "+String.valueOf(300) + " damage to the enemy. The enemy is stunned for 2 turns!");

                disabledstatus = true;
                statuscounter = 2;

                if(badHp < 0){ //even
                    txtLog.setText("Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The hero wins!");
                    heroHp = 2000;
                    badHp = 2500;
                    turnNumber= 1;
                    btnNextTurn.setText("Reset Game");
                }
                buttoncounter=12;


                break;
            case R.id.btnNxtTurn:


                if(turnNumber % 2 == 1){ //odd
                    badHp = badHp - herodps;
                    turnNumber++;
                    txtMonsHP.setText(String.valueOf(badHp));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    txtLog.setText("Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy!");

                    if(badHp < 0){ //even
                        txtLog.setText("Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The hero wins!");
                        heroHp = 2000;
                        badHp = 2500;
                        turnNumber= 1;
                        buttoncounter=0;
                        btnNextTurn.setText("Reset");
                    }

                    buttoncounter--;
                }
                else if(turnNumber%2 != 1){ //even

                    if(disabledstatus==true){
                        txtLog.setText("The enemy is still stunned for "+String.valueOf(statuscounter)+ " turns");
                        statuscounter--;
                        turnNumber++;
                        btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");
                        if(statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    else{
                        heroHp = heroHp - monsdps;
                        turnNumber++;
                        txtHeroHP.setText(String.valueOf(heroHp));
                        btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                        txtLog.setText("The villain "+String.valueOf(badName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy.");

                        if(heroHp < 0){
                            txtLog.setText("The villain "+String.valueOf(badName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy. Game Over");
                            heroHp = 2000;
                            badHp = 2500;
                            turnNumber= 1;
                            buttoncounter=0;
                            btnNextTurn.setText("Reset");
                        }
                    }
                    // buttoncounter--;
                }
                break;
        }
    }

}