echo off  
  
echo [INFO]�޸İ汾����ű� ��������Ҫ�����İ汾����v1.0.1):  
  
set /p newVersion=   
  
echo [INFO]����%newVersion% ��ʼ�滻�汾  
  
call mvn clean versions:set -DnewVersion=%newVersion%  
  
echo [INFO]�����Ƿ�������ģ�鶼�����汾�ɹ� �°汾Ϊ%newVersion%  