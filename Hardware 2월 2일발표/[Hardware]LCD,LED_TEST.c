//-----------------------------------------------------
#define LCD       PORTB           //LCD출력 포트
#define LCD_DATA  (LCD |= 0x01)   // RS=1
#define LCD_INST  (LCD &= ~0x01)  // RS=0
#define LCD_EN    (LCD |= 0x02)   // LCD enable E=1
#define LCD_DIS   (LCD &= ~0x02)  // LCD disable E=0
//-----------------------------------------------------

#include <iom128.h>
char person[6][15]={{"Colonel Mustard"},{"Professor Plum"},{"Mr.Green"},{"Mrs.peacock"},{"Miss Scarlet"},{"Mrs.White"}};              //용의자 이름 배열
char place[9][13]={{"Hall"},{"Lounge"},{"Dining Room"},{"Kitchen"},{"Conservatory"},{"Libary"},{"Study"},{"Billiard Room"},{"CLUE"}}; //장소 배열
char iteam[6][11]={{"Knife"},{"Candlestick"},{"Revolver"},{"Rope"},{"Lead Pipe"},{"Wrench"}};                                         //도구 배열
void delay_us(unsigned int time_us)            //time delay for us
{ register unsigned int i;
  for(i = 0; i < time_us; i++)               // 4 cycle +
  {
    asm (" PUSH  R0 ");        // 2 cycle +
    asm (" POP   R0 ");        // 2 cycle +
    asm (" PUSH  R0 ");        // 2 cycle +
    asm (" POP   R0 ");        // 2 cycle +
    asm (" PUSH  R0 ");        // 2 cycle +
    asm (" POP   R0 ");        // 2 cycle +

     }
}
 
 
 void delay_ms(int time_ms)        //time delay for ms
{
        register unsigned int i;

        for(i = 0; i < time_ms; i++)
        {
          
                delay_us(250);
                delay_us(250);
                delay_us(250);
                delay_us(250);
        }
}

void LCD_command(unsigned char command)  /* write a command(instruction) to text LCD */
{
  PORTD = LCD_DIS;      // E = 0
  PORTD = LCD_INST;     // Rs = 0
  LCD = command;        // output command
  PORTD = LCD_EN;       // E = 1
  asm (" PUSH  R0 ");   // delay for about 250 ns
  asm (" POP   R0 ");

  PORTD = LCD_DIS;      // E = 0
  delay_us(50);
}

void LCD_data(unsigned char data)  /* display a character on text LCD */
{
  PORTD = LCD_DIS;      // E = 0
  PORTD = LCD_DATA;     // Rs = 1
  LCD = data;           // output data
  PORTD = LCD_EN;       // E = 1
  asm (" PUSH  R0 ");   // delay for about 250 ns
  asm (" POP   R0 ");

  PORTD = LCD_DIS;      // E = 0
  delay_us(50);
}

 

void LCD_string(unsigned char command, unsigned char *string) /* display a string on LCD */
{
  LCD_command(command);    // start position of string
  while(*string != '\0')   // display string
    {
   LCD_data(*string);
  string++;
    }
}

void LCD_initialize(void)   /* initialize text LCD module */
{
  PORTD = LCD_EN;   // E = 1
  PORTD = LCD_DATA; // Rs = 1 (dummy write)
  PORTD = LCD_DIS;    // E = 0, Rs = 1
  delay_ms(2);

  LCD_command(0x38);    // function set(8 bit, 2 line, 5x7 dot)
  LCD_command(0x0C);    // display control(display ON, cursor OFF)
  LCD_command(0x06);    // entry mode set(increment, not shift)
  LCD_command(0x01);    // clear display
  delay_ms(2);
}

int main()              //LCD 및 LED TEST
{
  int led[8]={0x00,0x09,0x12,0x1B,0x24,0x2D,0x36,0x3F};    //000000,001001,010010,011011,100100,101101,110110,111111
  int i,j,z;
  DDRF = 0xff;
  DDRB = 0Xff;
  DDRD = 0xff;
  while(1)
  {
   for(i=0;i<6;i++)
     for(j=0;j<9;j++)
       for(z=0;z<6;z++)
       {
          LCD_initialize();
          LCD_command(0x80);
          PORTF = led[z];                         //논리 회로를 통한 LED제어 TEST
          LCD_string(0x80,"     C  L  U  E");     //머릿말 출력
          LCD_string(0xC0,person[i]);             //누가(WHO)
          LCD_string(0x94,place[j]);              //어디서(WHERE)
          LCD_string(0xd4,iteam[z]);              //무엇으로(WHAT)
          delay_ms(1000);
       }
  }
  return 0;
}



