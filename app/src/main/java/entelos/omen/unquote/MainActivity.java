package entelos.omen.unquote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentQuestionIndex;
    int totalCorrect;
    int totalQuestions;
    ArrayList<Question> questions;

    ImageView questionImageView;
    TextView questionTextView;
    TextView questionsRemaining;
    Button answer0Button;
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_unquote_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setElevation(0);

        questionImageView = findViewById(R.id.question_image);
        questionTextView = findViewById(R.id.question_title);
        questionsRemaining = findViewById(R.id.questions_remaining);

        answer0Button = findViewById(R.id.answer_0);
        answer1Button = findViewById(R.id.answer_1);
        answer2Button = findViewById(R.id.answer_2);
        answer3Button = findViewById(R.id.answer_3);
        submitButton = findViewById(R.id.submit_answer);

        answer0Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerSelected(0);
            }
        });
        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerSelected(1);
            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerSelected(2);
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerSelected(3);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerSubmission();
            }
        });

        startNewGame();
    }


    public void startNewGame() {
        questions = new ArrayList<>();
        Toast.makeText(this, String.valueOf(R.drawable.img_quote_0), Toast.LENGTH_LONG);
        Question firstQuestion = new Question(R.drawable.img_quote_0, "Insanely inspiring, insanely incorrect(maybe). \n Who is the true source of this inspiration ?", "Nelson Mandela", "Harriet Tubman", "Mahatma Gandhi", "Nicholas Klein", 3);
        Question secondQuestion = new Question(R.drawable.img_quote_1, "A peace worth striving for \n who actually reminded us of this ?", "Malala Yousafzai", "Martin Luther King Jr.", "Liu Xiaobo", "Dalai Lama", 1);
        Question thirdQuestion = new Question(R.drawable.img_quote_2, "Unfortunately, true but did Marilyn Monroe \n convey it or did someone else ?", "Laurel Thatcher Ulrich", "Eleanor Roosevelt", "Marilyn Monroe", "Queen Victoria", 0);
        Question fourthQuestion = new Question(R.drawable.img_quote_3, "Pretty good advice, \n perhaps a scientist did say it… Who actually did ?", "Albert Einstein", "Isaac Newton", "Rita Mae Brown", "Rosalind Franklin", 2);
        Question fifthQuestion = new Question(R.drawable.img_quote_4, "Was honest Abe honestly quoted? \n Who authored this pithy bit of wisdom ?", "Edward Stieglitz", "Maya Angelou", "Abraham Lincoln", "Ralph Waldo Emerson", 0);
        Question sixthQuestion = new Question(R.drawable.img_quote_5, "Easy advice to read, difficult advice to follow \n who actually said it ?", "Martin Luther King Jr.", "Mother Teresa", "Fred Rogers", "Oprah Winfrey", 1);
        //ArrayList<Question> newGame = new ArrayList<>();
        questions.add(firstQuestion);
        questions.add(secondQuestion);
        questions.add(thirdQuestion);
        questions.add(fourthQuestion);
        questions.add(fifthQuestion);
        questions.add(sixthQuestion);

        totalCorrect = 0;
        totalQuestions = questions.size();

        while (questions.size() > 10) {
            int randomIndex = generateRandomNumber(questions.size());
            questions.remove(randomIndex);
        }

        Question Question0 = chooseNewQuestion();

        displayQuestionsRemaining(questions.size());

        displayQuestion(Question0);
    }


    public Question chooseNewQuestion() {
        currentQuestionIndex = generateRandomNumber(2);
        return getCurrentQuestion();
    }

    public Question getCurrentQuestion() {

        return questions.get(currentQuestionIndex);
    }


    public void onAnswerSubmission() {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion.isCorrect()) {
            totalCorrect = totalCorrect + 1;
        }

        if (currentQuestion.playerAnswer == -1) {
            return;
        }

        questions.remove(currentQuestion);

        displayQuestionsRemaining(questions.size());

        if (questions.size() == 0) {
            String gameOverMessage = getGameOverMessage(totalCorrect, totalQuestions);

            AlertDialog.Builder gameOverDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            gameOverDialogBuilder.setCancelable(false);
            gameOverDialogBuilder.setTitle("Game over!");
            gameOverDialogBuilder.setMessage(gameOverMessage);
            gameOverDialogBuilder.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startNewGame();
                }
            });
            gameOverDialogBuilder.create().show();
        } else {
            chooseNewQuestion();

            displayQuestion(getCurrentQuestion());
        }
    }

    int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }

    String getGameOverMessage(int totalCorrect, int totalQuestions) {
        if (totalCorrect == totalQuestions) {
            return "You got all " + totalQuestions + " right! You won!";
        } else {
            return "You got " + totalCorrect + " right out of " + totalQuestions + ". Better luck next time!";
        }
    }


    public void displayQuestion(Question currentQuestion) {

        questionImageView.setImageResource(currentQuestion.imageId);
        questionTextView.setText(currentQuestion.questionText);
        answer0Button.setText(currentQuestion.answer0);
        answer1Button.setText(currentQuestion.answer1);
        answer2Button.setText(currentQuestion.answer2);
        answer3Button.setText(currentQuestion.answer3);
    }


    private void displayQuestionsRemaining(int questionsRemainingCount) {
        questionsRemaining.setText(String.valueOf(questionsRemainingCount));
        //questionsRemaining.setText("hello");
    }

    @SuppressLint("SetTextI18n")
    public void onAnswerSelected(int answerSelected) {
        Question currentQuestion = getCurrentQuestion();
        currentQuestion.playerAnswer = answerSelected;
        answer0Button.setText(currentQuestion.answer0);
        answer1Button.setText(currentQuestion.answer1);
        answer2Button.setText(currentQuestion.answer2);
        answer3Button.setText(currentQuestion.answer3);

        switch (answerSelected) {
            case 0:
                answer0Button.setText("✔ " + currentQuestion.answer0);
                break;
            case 1:
                answer1Button.setText("✔ " + currentQuestion.answer1);
                break;
            case 2:
                answer2Button.setText("✔ " + currentQuestion.answer2);
                break;
            case 3:
                answer3Button.setText("✔ " + currentQuestion.answer3);
                break;
        }
    }


}


class Main {
    public static void main(String[] args) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.startNewGame();
        System.out.println("Questions remaining: " + mainActivity.totalQuestions);
        Question currentQuestion = mainActivity.getCurrentQuestion();
        printQuestion(currentQuestion);
    }

    static void printQuestion(Question question) {
        System.out.println("Question: " + question.questionText);
        System.out.println("Option 1: " + question.answer0);
        System.out.println("Option 2: " + question.answer1);
        System.out.println("Option 3: " + question.answer2);
        System.out.println("Option 4: " + question.answer3);
    }
}