import React from 'react';
import styled from 'styled-components';

// コンテナ全体のスタイル
const Container = styled.div`
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
`;

// ヘッダーのスタイル
const Header = styled.h1`
  font-size: 3rem;
  margin-bottom: 1rem;
`;

// サブヘッダーのスタイル
const SubHeader = styled.h2`
  font-size: 1.5rem;
  margin-bottom: 2rem;
`;

// ボタンのスタイル
const Button = styled.button`
  padding: 1rem 2rem;
  font-size: 1.2rem;
  color: #764ba2;
  background: white;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: background 0.3s ease;

  &:hover {
    background: #f0f0f0;
  }
`;

function App() {
  return (
    <Container>
      <Header>Welcome to My Cool Webpage</Header>
      <SubHeader>Built with React and styled-components</SubHeader>
      <Button>Get Started</Button>
    </Container>
  );
}

export default App;
