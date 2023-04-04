package kh.study.tetris;

public class Testing {
/*
 *
 *
	// Method About Tetris
	void generateBlock() {
		// decide Block generateShape
		piece_kind = piece_kind_Next;
		piece_kind_Next = random(7);
		piece_rotation = 0;
		// decide Block generateLocation
		piece_Y = Tetris_Board_StartPoint_Y + tetris_Piece_GenerateLocation[piece_kind][0];
		piece_X = Tetris_Board_StartPoint_X + tetris_Piece_GenerateLocation[piece_kind][1];

		boolean allow_generate = true;

		for (i = piece_Y; i < piece_Y + 4; i++)
			for (j = piece_X; j < piece_X + 4; j++)
				if (tetris_Board[i][j] + tetris_Piece_shape[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
					allow_generate = false;


		// GAME OVER!!
		if (!allow_generate) {
			is_mode_Tetris = false;
			toggleBGM = false;
			togglebtSerial = false;
			delay(1500);

			// set android layout to ConversationView
			btSerial.write('r');

			// convert display to electricsign mode
			for (; servo_position >= 70; servo_position -= 1) {
				servo.write(servo_position);
				delay(30);
			}

			// empty btSerial buffer
			while (btSerial.available())
				btSerial.read();

			for (i = 0; i < sizeof(buffer_gameover); i++)
				buffer_readMessage[i] = buffer_gameover[i];
			buffer_readMessage[19] = score / 10 + 48;
			if (!(score / 10))
				buffer_readMessage[19] = score % 10 + 48;
			else
				buffer_readMessage[20] = score % 10 + 48;
			delay(50);
			index_char = 0;
			index_arr = 0;
			is_mode_Electricsign = true;

			thisNote = 0;
			thisNoteMax = 0;
			toggleGOM = true;

			while (toggleGOM || !index_char == 0) {
				t.update();
			}
			// all of the gameover melody played, reset Arduino!
			resetFunc();
		}


	}

	void moveDown_Block() {
		// non-landed
		boolean allow_move = true;
		for (i = piece_Y; i < piece_Y + 4; i++)
			for (j = piece_X; j < piece_X + 4; j++)
				if (tetris_Board[i + 1][j]
						+ tetris_Piece_shape[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
					allow_move = false;

		if (allow_move) {
			piece_Y += 1;
		}
		// temporary-landed
		else {
			int landed_piece_Y = piece_Y;
			int landed_piece_X = piece_X;
			int landed_ratation = piece_rotation;
			gamedelay = gamedelay_MAX;
			while (gamedelay) {
				t.update();

				// evade landing
				if (landed_piece_Y != piece_Y || landed_piece_X != piece_X || landed_ratation != piece_rotation) {
					break;
				}
				// completely-landed
				if (!gamedelay) {
					// stack block
					for (i = piece_Y; i < piece_Y + 4; i++)
						for (j = piece_X; j < piece_X + 4; j++)
							tetris_Board[i][j] += tetris_Piece_shape[piece_kind][piece_rotation][i - piece_Y][j
									- piece_X];

					clearLine();
					generateBlock();
				}
			}
		}
	}

	void land_Block() {
		for (k = 0; k < Tetris_Board_Y_MAX; k++) {
			boolean allow_move = true;
			for (i = piece_Y; i < piece_Y + 4; i++)
				for (j = piece_X; j < piece_X + 4; j++)
					if (tetris_Board[i + 1][j]
							+ tetris_Piece_shape[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2) {
						allow_move = false;
					}

			if (allow_move) {
				piece_Y += 1;
			}
		}
	}

	void clearLine(){
		//reset combo
		combo=0;

		char l;
		for(l=3; l<Tetris_Board_Y_MAX-1; l++){
		boolean isfulled=true;
		for(j=1; j<9; j++){
		if(!tetris_Board[l][j])
		isfulled=false;
		}
		if(isfulled){
		//twinkle line
		char disp_0_twinkle[8];
		char disp_1_twinkle[8];
		for(j=0; j<8; j++){
		disp_0_twinkle[j]=disp_0[j];
		disp_1_twinkle[j]=disp_1[j];
		}
		if(l<=10)
		disp_0_twinkle[l-11]=0;
		else
		disp_1_twinkle[l-3]=0;

		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
		delay(100);
		tone(Speaker,NOTE_B5+(NOTE_B5*combo),100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
		delay(100);
		tone(Speaker,NOTE_E6+(NOTE_E6*combo),400);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
		delay(100);
		noTone(Speaker);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
		delay(100);
		for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
		delay(100);

		//increase score
		if(!combo)  combo=1;
		else  combo=combo*2;

		score+=combo;
		delay(1);
		//refresh disps 3
		refresh_disp_3_Tetris();
		for (i = 0; i < 8; i++){
		Write_Max7219_3(i+1, disp_3[i]);
		}

		//fall lines
		for(j=l; j>=3; j--)
		for(k=0; k<10; k++)
		tetris_Board[j][k]=tetris_Board[j-1][k];
		//clean line
		char temp[10]={1, 0, 0, 0, 0, 0, 0, 0, 0, 1};
		for(j=0; j<10; j++)
		tetris_Board[3][j]=temp[j];
		}
		}

		//set difficulty
		gamedelay=(gamedelay_MAX-(gamedelay_difficulty*(score/score_difficulty)));
		if(gamedelay<20)
		gamedelay=20;
		Serial.print("gamedelay : ");
		Serial.println(gamedelay);
	}

int a() {
aaa;
}

}






























//Arduino's default libraries. Do not need to add.
#include <SoftwareSerial.h>
#include <EEPROM.h>
#include <Servo.h>
//Need to add libraries.
#include <Timer.h>
#include "pitches.h"
#include <MemoryFree.h>





//Definition About Tetris board
#define Tetris_Board_StartPoint_Y 3
#define Tetris_Board_StartPoint_X 1
#define Tetris_Board_Y_MAX 20
#define Tetris_Board_X_MAX 10


















//Method About Tetris
void generateBlock(){
//decide Block generateShape
piece_kind=piece_kind_Next;
piece_kind_Next=random(7);
piece_rotation=0;
//decide Block generateLocation
piece_Y=Tetris_Board_StartPoint_Y+tetris_Piece_GenerateLocation[piece_kind][0];
piece_X=Tetris_Board_StartPoint_X+tetris_Piece_GenerateLocation[piece_kind][1];

boolean allow_generate=true;

for(i=piece_Y; i<piece_Y+4; i++)
  for(j=piece_X; j<piece_X+4; j++)
    if(tetris_Board[i][j]+tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2)
      allow_generate=false;

//GAME OVER!!
if(!allow_generate){
  is_mode_Tetris=false;
  toggleBGM=false;
  togglebtSerial=false;
  delay(1500);

  //set android layout to ConversationView
  btSerial.write('r');

  //convert display to electricsign mode
  for( ; servo_position>=70; servo_position-=1)
  {
    servo.write(servo_position);
    delay(30);
  }

  //empty btSerial buffer
  while(btSerial.available()) btSerial.read();

  for(i=0; i<sizeof(buffer_gameover); i++)
    buffer_readMessage[i]=buffer_gameover[i];
  buffer_readMessage[19]=score/10+48;
  if(!(score/10))
    buffer_readMessage[19]=score%10+48;
  else
    buffer_readMessage[20]=score%10+48;
  delay(50);
  index_char=0;
  index_arr=0;
  is_mode_Electricsign=true;

  thisNote=0;
  thisNoteMax=0;
  toggleGOM=true;

  while(toggleGOM || !index_char==0){
    t.update();
  }
  //all of the gameover melody played, reset Arduino!
  resetFunc();
}
}
void moveLeft_Block(){
boolean allow_move=true;

for(i=piece_Y; i<piece_Y+4; i++)
  for(j=piece_X; j<piece_X+4; j++)
    if(tetris_Board[i][j-1]+tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2)
      allow_move=false;

if(allow_move)
  piece_X-=1;
}
void moveRight_Block(){
boolean allow_move=true;

for(i=piece_Y; i<piece_Y+4; i++)
  for(j=piece_X; j<piece_X+4; j++)
    if(tetris_Board[i][j+1]+tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2)
      allow_move=false;

if(allow_move)
  piece_X+=1;
}
void spinRight_Block(){
boolean allow_rotate=true;

for(i=piece_Y; i<piece_Y+4; i++)
  for(j=piece_X; j<piece_X+4; j++)
    if(piece_rotation<3){
      if(tetris_Board[i][j]+tetris_Piece_shape[piece_kind][piece_rotation+1][i-piece_Y][j-piece_X]>=2)
        allow_rotate=false;
    }
    else{
      if(tetris_Board[i][j]+tetris_Piece_shape[piece_kind][0][i-piece_Y][j-piece_X]>=2)
        allow_rotate=false;
    }

if(allow_rotate){
  if(piece_rotation<3)
    piece_rotation++;
  else
    piece_rotation=0;
}
}
void moveDown_Block(){
//non-landed
boolean allow_move=true;
for(i=piece_Y; i<piece_Y+4; i++)
  for(j=piece_X; j<piece_X+4; j++)
    if(tetris_Board[i +1][j]+tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2)
      allow_move=false;

if(allow_move){
  piece_Y+=1;
}
//temporary-landed
else{
  int landed_piece_Y=piece_Y;
  int landed_piece_X=piece_X;
  int landed_ratation=piece_rotation;
  gamedelay=gamedelay_MAX;
  while(gamedelay){
    t.update();

    //evade landing
    if(landed_piece_Y!=piece_Y || landed_piece_X!=piece_X || landed_ratation!=piece_rotation){
      break;
    }
    //completely-landed
    if(!gamedelay){
      //stack block
      for(i=piece_Y; i<piece_Y+4; i++)
        for(j=piece_X; j<piece_X+4; j++)
          tetris_Board[i][j]+=tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X];

      clearLine();
      generateBlock();
    }
  }
}
}
void land_Block(){
for(k=0; k<Tetris_Board_Y_MAX; k++){
  boolean allow_move=true;
  for(i=piece_Y; i<piece_Y+4; i++)
    for(j=piece_X; j<piece_X+4; j++)
      if(tetris_Board[i +1][j]+tetris_Piece_shape[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2){
        allow_move=false;
      }

  if(allow_move){
    piece_Y+=1;
  }
}
}
void clearLine(){
//reset combo
combo=0;

char l;
for(l=3; l<Tetris_Board_Y_MAX-1; l++){
  boolean isfulled=true;
  for(j=1; j<9; j++){
    if(!tetris_Board[l][j])
      isfulled=false;
  }
  if(isfulled){
    //twinkle line
    char disp_0_twinkle[8];
    char disp_1_twinkle[8];
    for(j=0; j<8; j++){
      disp_0_twinkle[j]=disp_0[j];
      disp_1_twinkle[j]=disp_1[j];
    }
    if(l<=10)
      disp_0_twinkle[l-11]=0;
    else
      disp_1_twinkle[l-3]=0;

    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
    delay(100);
    tone(Speaker,NOTE_B5+(NOTE_B5*combo),100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
    delay(100);
    tone(Speaker,NOTE_E6+(NOTE_E6*combo),400);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
    delay(100);
    noTone(Speaker);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0_twinkle[k]);  Write_Max7219_1(k+1, disp_1_twinkle[k]);  }
    delay(100);
    for (k=0; k<8; k++){  Write_Max7219_0(k+1, disp_0[k]);  Write_Max7219_1(k+1, disp_1[k]);  }
    delay(100);

    //increase score
    if(!combo)  combo=1;
    else  combo=combo*2;

    score+=combo;
    delay(1);
    //refresh disps 3
    refresh_disp_3_Tetris();
    for (i = 0; i < 8; i++){
      Write_Max7219_3(i+1, disp_3[i]);
    }

    //fall lines
    for(j=l; j>=3; j--)
      for(k=0; k<10; k++)
        tetris_Board[j][k]=tetris_Board[j-1][k];
    //clean line
    char temp[10]={1, 0, 0, 0, 0, 0, 0, 0, 0, 1};
    for(j=0; j<10; j++)
      tetris_Board[3][j]=temp[j];
  }
}

//set difficulty
gamedelay=(gamedelay_MAX-(gamedelay_difficulty*(score/score_difficulty)));
if(gamedelay<20)
  gamedelay=20;
Serial.print("gamedelay : ");
Serial.println(gamedelay);
}





//Method About Display
void refresh_disps_ElectricSign(){
int s;
for(s=7; s>0; s--)
  disp_0[s]=disp_0[s-1];
disp_0[0]=disp_1[7];

for(s=7; s>0; s--)
  disp_1[s]=disp_1[s-1];
disp_1[0]=disp_2[7];

for(s=7; s>0; s--)
  disp_2[s]=disp_2[s-1];
disp_2[0]=disp_3[7];

for(s=7; s>0; s--)
  disp_3[s]=disp_3[s-1];

//print ascii_font on electricsign's right edge.
if(32<=buffer_readMessage[index_char]&&buffer_readMessage[index_char]<=126){
  disp_3[0] = pgm_read_word_near(&(ascii_font_5x8[buffer_readMessage[index_char]-32][index_arr]));
}
else{
  disp_3[0] = pgm_read_word_near(&(ascii_font_5x8[0][index_arr]));
}
//print a blank between font and font
if(index_arr==5)
  disp_3[0] = 0x00;

index_arr++;

if(index_arr>5){
  index_arr=0;
  index_char++;
}
if(buffer_readMessage[index_char]==0){
  index_char=0;

  //If Message==tetris Game Start After print on electricsign Once
  boolean is_buffer_tetris=true;
  char t[6]="tetris";
  for(s=0; s<6; s++){
    if(buffer_readMessage[s]!=t[s])
      is_buffer_tetris=false;
  }

  //GAME START!!
  if(is_buffer_tetris){
    is_mode_Electricsign=false;

    //convert display to tetris mode
    for( ; servo_position<=160; servo_position+=1)
    {
      servo.write(servo_position);
      delay(30);
    }

    //set android layout to joystick
    btSerial.write('s');

    //load nextblock's random seed
    unsigned int seed=EEPROM.read(0);
    if(seed>400)
      seed-=400;
    else
      seed+=44;
    randomSeed(millis());
    for(i=0; i<seed; i++)
      piece_kind_Next=random(7);
    EEPROM.write(0, seed);
    //BGM on
    thisNote=0;
    thisNoteMax=0;
    toggleBGM=true;
    //generate first block
    generateBlock();
    gamedelay=gamedelay_MAX;

    is_mode_Tetris=true;
  }
}
}

void refresh_disp_0_Tetris(){
for(i=0; i<8; i++){
  char line=0;
  //from tetris_Board
  for(j=1; j<=8; j++){
    if(tetris_Board[i+11][j]){
      char dot=1;
      if(j<8){
        for(k=0; k<8-j; k++)
          dot*=2;
      }
      line+=dot;
    }
  }

  //form tetris_Piece_shape (Now Controling)
  if(i+11>=piece_Y && i+11<piece_Y+4){
    for(j=0; j<4; j++){
      if(0+tetris_Piece_shape[piece_kind][piece_rotation][i+11-piece_Y][j]){
        char dot=1;
        if(piece_X+j<8){
          for(k=0; k<8-piece_X-j; k++)
            dot*=2;
        }
        line+=dot;
      }
    }
  }

  disp_0[i]=line;
}
}
void refresh_disp_1_Tetris(){
for(i=0; i<8; i++){
  char line=0;
  //from tetris_Board
  for(j=1; j<=8; j++){
    if(tetris_Board[i+3][j]){
      char dot=1;
      if(j<8){
        for(k=0; k<8-j; k++)
          dot*=2;
      }
      line+=dot;
    }
  }

  //form tetris_Piece_shape (Now Controling)
  if(i+3>=piece_Y && i+3<piece_Y+4){
    for(j=0; j<4; j++){
      if(0+tetris_Piece_shape[piece_kind][piece_rotation][i+3-piece_Y][j]){
        char dot=1;
        if(piece_X+j<8){
          for(k=0; k<8-piece_X-j; k++)
            dot*=2;
        }
        line+=dot;
      }
    }
  }

  disp_1[i]=line;
}
}
void refresh_disp_2_Tetris(){
for(i=0; i<8; i++){
  char line=0;
  //from tetris_Piece_shape (piece_kind_Next)
  for(j=0; j<8; j++){
      if(tetris_Piece_shape[piece_kind_Next][0][i/2][j/2]){
        char dot=1;

        //if Next block is 'Square', it need just 2 blank
        if(piece_kind_Next==0){
          dot=2;
          for(k=0; k<6-j; k++)
            dot*=2;
        }
        //if Next block is 'I', it don't need any blank
        else if(piece_kind_Next==1){
          for(k=0; k<7-j; k++)
            dot*=2;
        }
        //else, need just 1 blank. view ex) 0 0 e e e e 0 0
        else{
          for(k=0; k<6-j; k++)
              dot*=2;
        }

        line+=dot;
      }
    }

  disp_2[i]=line;
}
}
void refresh_disp_3_Tetris(){
for(i=1; i<6; i++){
  char line=0;
  //from number_shape
  for(j=0; j<8; j++){

    //tens
    if(j<4 && score>9){
      if(number_shape[score/10][i-1][j]){
        char dot = 1;
        for(k=0; k<7-j; k++)
            dot*=2;
        line+=dot;
      }
    }


    //units. from number_shape
    if(j>=4){
      if(number_shape[score%10][i-1][j-4]){
        char dot = 1;
        for(k=0; k<7-j; k++)
            dot*=2;
        line+=dot;
      }
    }

  }

  disp_3[i]=line;
}
}

void Write_Max7219_0_byte(unsigned char DATA)
{
unsigned char i;
digitalWrite(Max7219_0_pinCS, LOW);
for (i = 8; i >= 1; i--)
{
  digitalWrite(Max7219_0_pinCLK, LOW);
  digitalWrite(Max7219_0_pinDIN, DATA & 0x80); // Extracting a bit data
  DATA = DATA << 1;
  digitalWrite(Max7219_0_pinCLK, HIGH);
}
}
void Write_Max7219_1_byte(unsigned char DATA)
{
unsigned char i;
digitalWrite(Max7219_1_pinCS, LOW);
for (i = 8; i >= 1; i--)
{
  digitalWrite(Max7219_1_pinCLK, LOW);
  digitalWrite(Max7219_1_pinDIN, DATA & 0x80); // Extracting a bit data
  DATA = DATA << 1;
  digitalWrite(Max7219_1_pinCLK, HIGH);
}
}
void Write_Max7219_2_byte(unsigned char DATA)
{
unsigned char i;
digitalWrite(Max7219_2_pinCS, LOW);
for (i = 8; i >= 1; i--)
{
  digitalWrite(Max7219_2_pinCLK, LOW);
  digitalWrite(Max7219_2_pinDIN, DATA & 0x80); // Extracting a bit data
  DATA = DATA << 1;
  digitalWrite(Max7219_2_pinCLK, HIGH);
}
}
void Write_Max7219_3_byte(unsigned char DATA)
{
unsigned char i;
digitalWrite(Max7219_3_pinCS, LOW);
for (i = 8; i >= 1; i--)
{
  digitalWrite(Max7219_3_pinCLK, LOW);
  digitalWrite(Max7219_3_pinDIN, DATA & 0x80); // Extracting a bit data
  DATA = DATA << 1;
  digitalWrite(Max7219_3_pinCLK, HIGH);
}
}

void Write_Max7219_0(unsigned char address, unsigned char dat)
{
digitalWrite(Max7219_0_pinCS, LOW);
Write_Max7219_0_byte(address); //address，code of LED
Write_Max7219_0_byte(dat); //data，figure on LED
digitalWrite(Max7219_0_pinCS, HIGH);
}
void Write_Max7219_1(unsigned char address, unsigned char dat)
{
digitalWrite(Max7219_1_pinCS, LOW);
Write_Max7219_1_byte(address); //address，code of LED
Write_Max7219_1_byte(dat); //data，figure on LED
digitalWrite(Max7219_1_pinCS, HIGH);
}
void Write_Max7219_2(unsigned char address, unsigned char dat)
{
digitalWrite(Max7219_2_pinCS, LOW);
Write_Max7219_2_byte(address); //address，code of LED
Write_Max7219_2_byte(dat); //data，figure on LED
digitalWrite(Max7219_2_pinCS, HIGH);
}
void Write_Max7219_3(unsigned char address, unsigned char dat)
{
digitalWrite(Max7219_3_pinCS, LOW);
Write_Max7219_3_byte(address); //address，code of LED
Write_Max7219_3_byte(dat); //data，figure on LED
digitalWrite(Max7219_3_pinCS, HIGH);
}










//Main
void setup()
{
Serial.begin(9600);
btSerial.begin(9600);

t.every(1, msec_clock);

pinMode(Max7219_0_pinCLK, OUTPUT);
pinMode(Max7219_0_pinCS, OUTPUT);
pinMode(Max7219_0_pinDIN, OUTPUT);
pinMode(Max7219_1_pinCLK, OUTPUT);
pinMode(Max7219_1_pinCS, OUTPUT);
pinMode(Max7219_1_pinDIN, OUTPUT);
pinMode(Max7219_2_pinCLK, OUTPUT);
pinMode(Max7219_2_pinCS, OUTPUT);
pinMode(Max7219_2_pinDIN, OUTPUT);
pinMode(Max7219_3_pinCLK, OUTPUT);
pinMode(Max7219_3_pinCS, OUTPUT);
pinMode(Max7219_3_pinDIN, OUTPUT);

delay(50);

//Initialize Max7219
Write_Max7219_0(0x09, 0x00); //decoding ：BCD
Write_Max7219_0(0x0a, 0x01); //brightness
Write_Max7219_0(0x0b, 0x07); //scanlimit；8 LEDs
Write_Max7219_0(0x0c, 0x01); //power-down mode：0，normal mode：1
Write_Max7219_0(0x0f, 0x00); //test display：1；EOT，display：0
Write_Max7219_1(0x09, 0x00); //decoding ：BCD
Write_Max7219_1(0x0a, 0x01); //brightness
Write_Max7219_1(0x0b, 0x07); //scanlimit；8 LEDs
Write_Max7219_1(0x0c, 0x01); //power-down mode：0，normal mode：1
Write_Max7219_1(0x0f, 0x00); //test display：1；EOT，display：0
Write_Max7219_2(0x09, 0x00); //decoding ：BCD
Write_Max7219_2(0x0a, 0x01); //brightness
Write_Max7219_2(0x0b, 0x07); //scanlimit；8 LEDs
Write_Max7219_2(0x0c, 0x01); //power-down mode：0，normal mode：1
Write_Max7219_2(0x0f, 0x00); //test display：1；EOT，display：0
Write_Max7219_3(0x09, 0x00); //decoding ：BCD
Write_Max7219_3(0x0a, 0x01); //brightness
Write_Max7219_3(0x0b, 0x07); //scanlimit；8 LEDs
Write_Max7219_3(0x0c, 0x01); //power-down mode：0，normal mode：1
Write_Max7219_3(0x0f, 0x00); //test display：1；EOT，display：0

servo.attach(A2);
servo.write(servo_position);

//check Max7219 condition
for (i=0; i<65; i++)
{
  for (j=0; j<8; j++)
  {
    Write_Max7219_0(j+1, disp_test[j]);
    Write_Max7219_1(j+1, disp_test[j]);
    Write_Max7219_2(j+1, disp_test[j]);
    Write_Max7219_3(j+1, disp_test[j]);
    delay(3);
  }
  for (j=0; j<8; j++)
    if(disp_test[j]>128){ disp_test[j]-=128;  break;  }
    else if(disp_test[j]>64){ disp_test[j]-=64; break;  }
    else if(disp_test[j]>32){ disp_test[j]-=32; break;  }
    else if(disp_test[j]>16){ disp_test[j]-=16; break;  }
    else if(disp_test[j]>8) { disp_test[j]-=8; break; }
    else if(disp_test[j]>4) { disp_test[j]-=4; break; }
    else if(disp_test[j]>2) { disp_test[j]-=2; break; }
    else if(disp_test[j]==1){ disp_test[j]-=1;  break;  }
}

////set Message IMPOSSIBLE is NOTHING
//for(i=0; i<sizeof(buffer_readMessage); i++)
//  EEPROM.write(i+1, buffer_readMessage[i]);

//load buffer_readMessage from EEPROM
for(i=0; i<sizeof(buffer_readMessage); i++)
  buffer_readMessage[i]=EEPROM.read(i+1);
}

void loop()
{
t.update();

if(is_mode_Electricsign){

  if(togglebtSerial==false)
    //empty btSerial buffer
    while(btSerial.available()) btSerial.read();

  //Receive Data by String (char+char+...+char)   available receive many bytes
  //Android -> HC-06 -> Arduino -> SerialMonitor
  int index_message=0;
  while(btSerial.available()) {
    //for read serial consecutively
    delay(1);

    char c = btSerial.read();
    buffer_readMessage[index_message]=c;

    //if message is too long, cut it sizeof(buffer_readMessage)-10
    if(index_message==sizeof(buffer_readMessage)-10){
      //empty btSerial buffer
      while(btSerial.available()) btSerial.read();
      break;
    }

    index_message++;
  }

  if (index_message!=0) {
    //ElectricSign STOP
    is_mode_Electricsign=false;

//    //You can Check Messages
//    Serial.print("readMessage:");
//    for(i=0; i<index_message; i++){
//      Serial.print(buffer_readMessage[i]);
//      Serial.print(",");
//    }
//    Serial.println("");

    buffer_readMessage[index_message++]=' ';
    buffer_readMessage[index_message++]=' ';
    buffer_readMessage[index_message++]=' ';
    buffer_readMessage[index_message++]=' ';
    buffer_readMessage[index_message++]=' ';
    buffer_readMessage[index_message++]=0;

    //save buffer_readMessage in EEPROM. buffer_readMessage is not 'tetris'
    boolean is_buffer_tetris=true;
    char t[6]="tetris";
    for(i=0; i<6; i++){
      if(buffer_readMessage[i]!=t[i])
        is_buffer_tetris=false;
    }
    if(!is_buffer_tetris){
      for(i=0; i<sizeof(buffer_readMessage); i++)
        EEPROM.write(i+1, buffer_readMessage[i]);
    }

    //reset the rest buffer
    for(i=index_message; i<sizeof(buffer_readMessage); i++)
      buffer_readMessage[i]=0;

    //reset display
    for(i=0; i<8; i++){
      disp_0[i] = 0;
      disp_1[i] = 0;
      disp_2[i] = 0;
      disp_3[i] = 0;
    }

    //ElectricSign reSTART
    index_arr=0;
    index_char=0;
    is_mode_Electricsign=true;
  }

  //Send Data          just Used for interrupt
  //SerialMonitor -> Arduino -> HC-06 -> Android
  if (Serial.available()) {
    btSerial.write(Serial.read());
  }

  //lock servo_position
  servo.write(servo_position);
}

if(is_mode_Tetris){
  //game event
  if(!gamedelay){
    moveDown_Block();
    gamedelay=gamedelay_MAX;
  }


  if(togglebtSerial==false)
    //empty btSerial buffer
    while(btSerial.available()) btSerial.read();

  //Receive Data by char          receive just 1 byte
  if(btSerial.available()) {
    char c = btSerial.read();  //gets one byte from serial buffer
    switch (c) {
      case 'F':
        land_Block();
        break;
      case 'L':
        moveLeft_Block();
        break;
      case 'S':
        spinRight_Block();
        break;
      case 'R':
        moveRight_Block();
        break;
    }
  }

  //lock servo_position
  servo.write(servo_position);
}
}










//Time Interrupt per 1ms
void msec_clock(){
//Interrupt per 500ms
if(millis()%500==0){
  //Read potentiometer that control 'Electricsign speed'
  pot_value=analogRead(Ohm_Reader);

  if(!is_mode_Tetris){
    if(pot_value>1010)
      is_mode_Electricsign=false;
    else
      is_mode_Electricsign=true;
  }

  //print memory
  Serial.print("freeMemory()=");
  Serial.println(freeMemory());
}



//Interrupt in Electricsign mode
if(is_mode_Electricsign){

  if(togglebtSerial==false)
    //empty btSerial buffer
    while(btSerial.available()) btSerial.read();

  if(!btSerial.available()){
    //printdelay control
    printdelay--;

    //display
    if(!(printdelay%20)){
      if(!printdelay){
        refresh_disps_ElectricSign();
        printdelay=(10+(pot_value/20));
      }
      for (i = 0; i < 8; i++){
        Write_Max7219_0(i+1, disp_0[i]);
        Write_Max7219_1(i+1, disp_1[i]);
        Write_Max7219_2(i+1, disp_2[i]);
        Write_Max7219_3(i+1, disp_3[i]);
      }
    }
  }
}

//Interrupt in Tetris mode
if(is_mode_Tetris){
  //gamedelay event
  gamedelay--;

  //display
  if(!(gamedelay%20)){
    refresh_disp_0_Tetris();
    refresh_disp_1_Tetris();
    refresh_disp_2_Tetris();
    refresh_disp_3_Tetris();
    for (i = 0; i < 8; i++){
      Write_Max7219_0(i+1, disp_0[i]);
      Write_Max7219_1(i+1, disp_1[i]);
      Write_Max7219_2(i+1, disp_2[i]);
      Write_Max7219_3(i+1, disp_3[i]);
    }
  }
}

//Interrupt if toggleMusic is toggled
if(toggleBGM){//tetris background melody
  if(!thisNoteMax){
    //play melody from 0 to size of melody
    if(thisNote!=(sizeof(background_noteDurations)/sizeof(uint8_t))){
      thisNoteMax=(1300-(30*(score/score_difficulty)))/background_noteDurations[thisNote];
      tone(Speaker, pgm_read_word_near(&(background_melody[thisNote])), (1000-(30*(score/score_difficulty)))/background_noteDurations[thisNote]);

      //reload next melody
      thisNote++;
    }
    //all of melody played. reset to melody's start point
    else{ //thisNote==(sizeof(background_melody)/sizeof(int))
      thisNote=0;
    }
  }
  else{
    thisNoteMax--;
  }
}
if(toggleGOM){//tetris gameover melody
  if(!thisNoteMax){
    //play melody from 0 to size of melody
    if(thisNote!=(sizeof(gameover_noteDurations)/sizeof(uint8_t))){
      thisNoteMax=1100/gameover_noteDurations[thisNote];
      tone(Speaker, pgm_read_word_near(&(gameover_melody[thisNote])), 1000/gameover_noteDurations[thisNote]);

      //reload next melody
      thisNote++;
    }
    //all of melody played. end sound
    else{
      toggleGOM=false;
    }
  }
  else{
    thisNoteMax--;
  }
}

*/
}